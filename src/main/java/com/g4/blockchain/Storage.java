package com.g4.blockchain;

public class Storage {
    private static Peers peers = new Peers();

    public Storage() {
    }

    public Peers getPeers() {
        return peers;
    }

    public Peers addPeer(Peer p) {
        peers.add(p);
        return peers;
    }

}
