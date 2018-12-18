package com.g4.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlockChain extends LinkedList<Block> {

    public static final int DIFFICULTY = 4;

    Logger logger = LoggerFactory.getLogger(BlockChain.class);

    public Boolean isChainValid(int difficulty) throws JsonProcessingException {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for(int i=1; i < this.size(); i++) {
            currentBlock = this.get(i);
            previousBlock = this.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }

    public BlockChain addTransaction(Transaction transaction) throws JsonProcessingException {
        if (this.size() == 0) {
            this.add(new Block("0"));
        }
        this.getLast().addTransaction(transaction);
        return this;
    }

    public BlockChain mine(String proofAlgo) throws JsonProcessingException {
        logger.info("Blockchain mining latest blog");
        this.get(this.size() - 1).mineBlock(DIFFICULTY, proofAlgo);
        String previousHash = this.get(this.size() - 1).getHash();
        this.add(new Block(previousHash));
        return this;
    }
}
