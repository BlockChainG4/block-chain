package com.g4.blockchain;

public class BlockChain {
    private Block head;

    public BlockChain() {
    }

    public BlockChain(Block head) {
        this.head = head;
    }

    public Block getHead() {
        return head;
    }

    public void setHead(Block head) {
        this.head = head;
    }
}
