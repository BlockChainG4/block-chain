package com.g4.blockchain.dto;

import com.g4.blockchain.Block;

import java.io.Serializable;
import java.util.List;

public class BlockChainResponseDto implements Serializable {
    private List<Block> blockchain;
    private int length;

    public BlockChainResponseDto() {}

    public BlockChainResponseDto(List<Block> chain) {
        this.blockchain = chain;
        this.length = chain.size();
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    public int getLength() {
        return length;
    }

    public void setBlockchain(List<Block> chain) {
        this.blockchain = chain;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
