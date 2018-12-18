package com.g4.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g4.blockchain.utilities.Hashing;

import java.util.Calendar;
import java.util.LinkedList;

public class Block {

    private String previousHash;
    private String hash;
    private long timeStamp;
    private int nonce;
    private LinkedList<Transaction> transactions;
    private String merkleRoot;

    public Block(String previousHash) throws JsonProcessingException {
        this(previousHash, new LinkedList<>());
    }

    public Block(String previousHash, LinkedList<Transaction> transactions) throws JsonProcessingException {
        this.timeStamp = Calendar.getInstance().getTimeInMillis();
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.hash = calculateHash();
    }

    public Block() {
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    //Calculate new hash based on blocks contents
    public String calculateHash() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String data = "";
        data += previousHash +
                Long.toString(timeStamp) +
                Integer.toString(nonce) +
                merkleRoot;

        data += "\"transactions\":";
        data += mapper.writeValueAsString(transactions);
        return Hashing.applySha256(data);
    }



    public void mineBlock(int difficulty) throws JsonProcessingException {
        this.timeStamp = Calendar.getInstance().getTimeInMillis();
        merkleRoot = Hashing.getMerkleRoot(transactions);
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!this.hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            this.hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + this.hash);
    }
    //Add transactions to this block
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }

    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public void setTransactions(LinkedList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }
}
