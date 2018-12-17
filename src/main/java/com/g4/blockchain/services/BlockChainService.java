package com.g4.blockchain.services;

import com.g4.blockchain.Block;
import com.g4.blockchain.BlockChain;
import org.springframework.stereotype.Service;

@Service
public class BlockChainService {

    public BlockChain getChain() {
        return null;
    }

    public void broadCastMining(String address) {
        // Call the address with RestTemplate to get the latest chain (getChain)
    }

    public void broadCastResult(Block block) {
        // Send the block to all other known peers if it's the latest one and begin mining the block
    }
}
