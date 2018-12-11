package com.g4.blockchain.peerresource;

import com.g4.blockchain.actionresource.Block;

import java.util.ArrayList;
import java.util.List;

public class PeerController {

    private List<Peer> peers = new ArrayList<>();
    private static final Block root = new Block(0, "ROOT_HASH");

    public Peer addPeer(String name, int port){
        Peer p = new Peer(name, "localhost", port, peers, root);
        p.startHost();
        System.out.println("PEERS NOW: " + peers.size());
        peers.add(p);
        System.out.println("PEERS AFTER: " + peers.size());
        return p;
    }

    public Peer getPeer(String name){
        for (Peer p: peers) {
            if (p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public List<Peer> getAllPeers(){
        return peers;
    }

    public List<Block> getPeerBlocks(String name) {
        Peer agent = getPeer(name);
        if (agent != null) {
            return agent.getBlockchain();
        }
        return null;
    }

    public Block createBlock(final String name) {
        Peer agent = getPeer(name);
        if (agent != null) {
            return agent.createBlock();
        }
        return null;
    }

    public void deletePeer(String name) {
        Peer p = getPeer(name);
        if (p != null) {
            p.stopHost();
            peers.remove(p);
        }
    }

    public void deleteAllPeers() {
        for (Peer p : peers) {
            p.stopHost();
        }
        peers.clear();
    }

}
