package com.g4.blockchain;

import java.util.Calendar;

public class Block {
    private String previousHash;

    private long timeStamp;

    public Block() {
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
}
