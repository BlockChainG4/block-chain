package com.g4.blockchain;

public class Storage {
    private Peers peers;
    private BlockChain blockChain;

    public Storage() {
        this.peers = new Peers();
    }

    public Peers getPeers() {
        return this.peers;
    }

    public void setPeers(Peers peers) {
        this.peers = peers;
    }
}
