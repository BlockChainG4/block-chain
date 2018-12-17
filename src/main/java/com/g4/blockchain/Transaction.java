package com.g4.blockchain;

import com.g4.blockchain.utilities.Hashing;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String hash;
    private String operation;
    private String result;


    public Transaction(String operation) {
        this.operation = operation;
    }

    public Transaction() {
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    // This Calculates the transaction hash (which will be used as its Id)
    private String calulateHash() {
        return Hashing.applySha256(operation + result);
    }
}
