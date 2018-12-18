package com.g4.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlockChain extends LinkedList<Block> {

    public static final int DIFFICULTY = 4;

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
            //check if hash is solved
            if(!currentBlock.getHash().substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    public BlockChain addTransaction(Transaction transaction) {
        this.get(this.size() - 1).addTransaction(transaction);
        return this;
    }

    public BlockChain mine() throws JsonProcessingException {
        this.get(this.size() - 1).mineBlock(DIFFICULTY);
        String previousHash = this.get(this.size() - 1).getHash();
        this.add(new Block(previousHash));
        return this;
    }

    public List<String> getBlocksAsString(ObjectMapper mapper) throws JsonProcessingException {
        List<String> blocks = new ArrayList<>();
        for (Block b : this) {
            blocks.add(mapper.writeValueAsString(b));
        }
        return blocks;
    }

}
