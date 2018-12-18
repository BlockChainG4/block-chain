package com.g4.blockchain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g4.blockchain.*;
import com.g4.blockchain.utilities.FileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
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

    @Value("${peer.self}")
    private String self;

    @Value("${blockchain.file.name}")
    private String blockChainFileName;

    public BlockChain getChain() throws IOException {
        // Read from file and get the entire block chain
        BlockChain chain = new BlockChain();
        List<String> blocks = FileWriter.readFileInList(blockChainFileName);
        if (blocks.size() != 0) {
            for (int i = blocks.size() - 1; i > 0; i--) {
                chain.add(mapper.readValue(blocks.get(i), Block.class));
            }
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
            FileWriter.save(newChain.getBlocksAsString(mapper), blockChainFileName);
            retryService.broadCastNewChain(self);
        }
    }

    // Begin mining a block
    public void broadCastResult(Block block) throws Exception {
        // Check if incoming block is newer than latest in chain
        // and that the previous hash is the same. If not, ignore
        BlockChain chain = getChain();
        Block latestChainBlock = chain.getLast();
        if (verifyBlock(block, latestChainBlock) && block.getPreviousHash().equals(latestChainBlock.getHash())) {
            Iterable<Peer> peers = peerRepository.findAll();
            peers.forEach(peer -> retryService.broadCastResult(peer.getAddress(), block));
            chain.add(block);
            chain.mine();
            FileWriter.save(chain.getBlocksAsString(mapper), blockChainFileName);
            for (Peer peer : peerRepository.findAll()) {
                retryService.broadCastNewChain(peer.getAddress());
            }
            // Filewriter read current blockchain
            // Append hash to list, write to file
            // broadcast mining is done.
        }
    }

    // Checks if the new chain is valid. Return true if it is
    public boolean consensus(BlockChain newChain, BlockChain ownChain) {
        if (verifyBlock(newChain.get(newChain.size() - 1), ownChain.get(ownChain.size() - 1))) {
            if (newChain.size() > ownChain.size()) {
                return true;
            }
        }
        return false;
    }

    public boolean verifyBlock(Block newBlock, Block ownBlock) {
        return newBlock.getTimeStamp() > ownBlock.getTimeStamp();
    }

    public BlockChain addTransaction(Transaction transaction) throws IOException, ScriptException {
        BlockChain chain = getChain();
        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
        transaction.setResult(engine.eval(transaction.getOperation()).toString());
        chain.addTransaction(transaction);
        return chain;
    }

    public BlockChain mine() throws IOException {
        BlockChain chain = getChain();
        for (Peer peer : peerRepository.findAll()) {
            retryService.broadCastResult(peer.getAddress(), chain.getLast());
        }
        chain.mine();
        return chain;
    }
}
