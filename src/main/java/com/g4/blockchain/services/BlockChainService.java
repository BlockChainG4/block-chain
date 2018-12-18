package com.g4.blockchain.services;

import com.g4.blockchain.Block;
import com.g4.blockchain.Transaction;
import com.g4.blockchain.utilities.Hasher;
import com.google.common.io.BaseEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class BlockChainService {

    private List<Block> blockChain = new ArrayList<>();
    private Hasher hasher;
    private List<Transaction> currentTransactions;
    private RestTemplate restTemplate;

    @Autowired
    public BlockChainService(Hasher hasher){
        this.currentTransactions = Collections.synchronizedList(new LinkedList<>());
        this.blockChain = Collections.synchronizedList(new LinkedList<>());
        this.hasher = hasher;
        this.restTemplate = new RestTemplate();
        createBlock(new Block(100, "1"));
    }

    public Block createBlock(Block block){
        //Creates a new Block and adds it to the chain
        block.setIndex(blockChain.size() + 1);
        block.setTimeStamp(System.currentTimeMillis());
        block.setTransactions(this.currentTransactions);
        if (block.getPreviousHash() == null) {
            block.setPreviousHash(hasher.hashBlock(block));
        }
        this.currentTransactions = Collections.synchronizedList(new LinkedList<>());
        this.blockChain.add(block);
        return block;
    }

    public int addTransaction(Transaction trans){
        this.currentTransactions.add(trans);
        trans.calculate();
        return this.blockChain.get(this.blockChain.size() - 1).getIndex() + 1;
    }

    public long proofOfWork(long lastProof) {
        long proof = 0;
        while(!validProof(lastProof, proof)) {
            proof++;
        }
        return proof;
    }

    public boolean validProof(long lastProof, long proof) {
        String guess = BaseEncoding.base64().encode(String.format("{%d}{%d}", lastProof, proof).getBytes(Charset.forName("UTF8")));
        String guessHash = hasher.hash(guess);
        return guessHash.substring(0, 4).equals("0000");
    }

    public List<Block> getBlockChain() {
        return Collections.unmodifiableList(this.blockChain);
    }

    public boolean isValidChain(List<Block> chain) {
        Block lastBlock = chain.get(0);
        Block block = null;
        int currentIndex = 1;

        while (currentIndex < chain.size()) {
            block = chain.get(currentIndex);
            if (!block.getPreviousHash().equals(hasher.hashBlock(lastBlock))) {
                return false;
            }
            if (!validProof(lastBlock.getProof(), block.getProof())) {
                return false;
            }
            currentIndex++;
        }
        return true;
    }

    public Block getLatestBlock(){
        if (this.blockChain.isEmpty()) {
            return null;
        }
        return this.blockChain.get(this.blockChain.size() - 1);
    }


}
