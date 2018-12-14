package com.g4.blockchain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Block implements Serializable {
    private String previousHash;
    private long timeStamp;
    private int index;
    private List<Transaction> transactions;
    private long proof;

    public Block() {
        this.timeStamp = Calendar.getInstance().getTimeInMillis();
    }

    public Block(long proof, String previousHash) {
        this.proof = proof;
        this.previousHash = previousHash;
        this.timeStamp = Calendar.getInstance().getTimeInMillis();
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getIndex() {
        return index;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public long getProof() {
        return proof;
    }
}
