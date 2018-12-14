package com.g4.blockchain.services;

import com.g4.blockchain.Block;
import com.g4.blockchain.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockChainService {

    private List<Block> blockChain = new ArrayList<>();

    public void newBlock(Block block){
        //TODO: Creates a new Block and adds it to the chain
    }

    public Transaction addTransaction(Transaction trans){
        //TODO: Adds new transaction to the list of transactions
//        blockChain.add(trans);
        return null;
    }

    public static void hash(Block block){
        //TODO: Hashes a block
    }

    private Block getLatestBlock(){
        if (blockChain.isEmpty()) {
            return null;
        }
        return blockChain.get(blockChain.size() - 1);
    }


}
