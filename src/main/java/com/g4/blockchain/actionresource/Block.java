package com.g4.blockchain.actionresource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Block {
    private int index;
    private String hash;
    private String prevHash;
    private Long timestamp;
//    private List<Transaction> transactions = new ArrayList<>();

    public Block(int index, String prevHash) {
        this.index = index;
        this.prevHash = prevHash;
        timestamp = System.currentTimeMillis();
        hash = calculateHash(prevHash + String.valueOf(timestamp));
    }

    public int getIndex() {
        return index;
    }

    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public Long getTimestamp() {
        return timestamp;
    }

//    public List<Message> getTransactions() {
//        return transactions;
//    }

    private String calculateHash(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return "HASH_ERROR";
        }

        final byte bytes[] = digest.digest(text.getBytes());
        final StringBuilder hexString = new StringBuilder();
        for (final byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public String toString() {
        return "Block{" +
                "hash='" + hash + '\'' +
                ", prevHash='" + prevHash + '\'' +
                ", timestamp=" + timestamp +
//                ", transactions=" + transactions +
                '}';
    }
}
