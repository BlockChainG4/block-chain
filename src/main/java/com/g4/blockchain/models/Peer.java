package com.g4.blockchain.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Peer {

    private String name;
    private String address;
    private int port;
    private List<Peer> peers;
    private List<Block> blockchain = new ArrayList<>();
    private ServerSocket serverSocket;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4); //this is the nodes?
    private boolean isListening = true;

    public Peer(String name, String address, int port, List<Peer> peers, Block root) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.peers = peers;
        blockchain.add(root);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public List<Peer> getPeers() {
        return peers;
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    public Block createBlock() {
        if (blockchain.isEmpty()) {
            return null;
        }

        Block previousBlock = getLatestBlock();
        if (previousBlock == null) {
            return null;
        }

        final int index = previousBlock.getIndex() + 1;
        final Block block = new Block(index, previousBlock.getHash());
        System.out.println(String.format("%s created new block %s", name, block.toString()));
        broadcast(Transaction.TRANSACTION_TYPE.INFO_NEW_BLOCK, block);
        return block;
    }

    private Block getLatestBlock() {
        if (blockchain.isEmpty()) {
            return null;
        }
        return blockchain.get(blockchain.size() - 1);
    }

    private void broadcast(Transaction.TRANSACTION_TYPE type, final Block block) {
        peers.forEach(peer -> sendMessage(type, peer.getAddress(), peer.getPort(), block));
    }

    private void sendMessage(Transaction.TRANSACTION_TYPE type, String host, int port, Block... blocks) {
        try (
                final Socket peer = new Socket(host, port);
                final ObjectOutputStream out = new ObjectOutputStream(peer.getOutputStream());
                final ObjectInputStream in = new ObjectInputStream(peer.getInputStream())) {
            Object fromPeer;
            while ((fromPeer = in.readObject()) != null) {
                if (fromPeer instanceof Transaction) {
                    final Transaction msg = (Transaction) fromPeer;
                    System.out.println(String.format("%d received: %s", this.port, msg.toString()));
                    if (Transaction.TRANSACTION_TYPE.READY == msg.type) {
                        out.writeObject(new Transaction.MessageBuilder()
                                .withType(type)
                                .withReceiver(port)
                                .withSender(this.port)
                                .withBlocks(Arrays.asList(blocks)).build());
                    } else if (Transaction.TRANSACTION_TYPE.RSP_ALL_BLOCKS == msg.type) {
                        if (!msg.blocks.isEmpty() && this.blockchain.size() == 1) {
                            blockchain = new ArrayList<>(msg.blocks);
                        }
                        break;
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println(String.format("Unknown host %s %d", host, port));
        } catch (IOException e) {
            System.err.println(String.format("%s couldn't get I/O for the connection to %s. Retrying...%n", getPort(), port));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
