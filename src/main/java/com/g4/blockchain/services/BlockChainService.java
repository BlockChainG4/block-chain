package com.g4.blockchain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g4.blockchain.*;
import com.g4.blockchain.utilities.FileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class BlockChainService {

    @Autowired
    private RetryService retryService;

    @Autowired
    private PeerRepository peerRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ScriptEngineManager scriptEngineManager;

    @Autowired
    private FileWriter fileWriter;

    @Value("${peer.self}")
    private String self;

    @Value("${blockchain.file.name}")
    private String blockChainFileName;

    @Value("${proof.algo}")
    private String proofAlgo;

    Logger logger = LoggerFactory.getLogger(BlockChainService.class);

    public BlockChain getChain() throws IOException {
        // Read from file and get the entire block chain
        BlockChain chain = fileWriter.readFileInList(blockChainFileName);
        if (chain.size() == 0) {
            chain.add(new Block("0"));
        }
        return chain;
    }

    // When mining is done
    public void broadCastMining(String address) throws Exception {
        BlockChain newChain = retryService.getLatestChain(address);

        BlockChain ownChain = getChain();

        if (consensus(newChain, ownChain)) {
            // It is newer than the one we have here
            // so we save it to a file and broadcast it to all out peers that we have a newer one
            fileWriter.save(newChain, blockChainFileName);
            retryService.broadCastNewChain(self);
        }
    }

    // Begin mining a block
    public void broadCastResult(BlockChain incomingChain) throws Exception {
        // Check if incoming block is newer than latest in chain
        // and that the previous hash is the same. If not, ignore
        BlockChain chain = getChain();
        logger.info("Checking if block is valid/newer");
        if (consensus(incomingChain, chain)) {
            logger.info("Block is newer.. Notifying peers and mining block");
            fileWriter.save(incomingChain, blockChainFileName);
            Iterable<Peer> peers = peerRepository.findAll();
            peers.forEach(peer -> retryService.broadCastResult(peer.getAddress(), incomingChain));
            chain.mine(proofAlgo);
            fileWriter.save(incomingChain, blockChainFileName);
            for (Peer peer : peerRepository.findAll()) {
                retryService.broadCastNewChain(peer.getAddress());
            }
            // Filewriter read current blockchain
            // Append hash to list, write to file
            // broadcast mining is done.
        }
    }

    // Checks if the new chain is valid. Return true if it is
    public boolean consensus(BlockChain newChain, BlockChain ownChain) throws JsonProcessingException {
        if(newChain.size() > ownChain.size()) {
            return newChain.isChainValid(BlockChain.DIFFICULTY);
        }

        return false;
    }

    public BlockChain addTransaction(Transaction transaction) throws Exception {
        BlockChain chain = getChain();
        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
        transaction.setResult(engine.eval(transaction.getOperation()).toString());
        chain.addTransaction(transaction);
        fileWriter.save(chain, blockChainFileName);
        logger.info("Transaction added to block: " + chain.getLast().getTransactions().get(0).getOperation());
        return chain;
    }

    public BlockChain mine() throws Exception {
        BlockChain chain = getChain();
        for (Peer peer : peerRepository.findAll()) {
            logger.info("Broadcasting new block to be mined");
            retryService.broadCastResult(peer.getAddress(), chain);
        }
        chain = getChain();
        chain.mine(proofAlgo);

        BlockChain storedChain = getChain();
        if (consensus(chain, storedChain)) {
            fileWriter.save(chain, blockChainFileName);
            for (Peer peer : peerRepository.findAll()) {
                retryService.broadCastNewChain(peer.getAddress());
            }
            return chain;
        }
        return storedChain;
    }
}
