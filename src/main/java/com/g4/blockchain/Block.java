package com.g4.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.LinkedList;

public class Block {

    @Inject
    ObjectMapper mapper;

    private String previousHash;
    private String hash;
    private long timeStamp;
    private int nonce;
    private LinkedList<Transaction> transactions;

    public Block(String previousHash) throws JsonProcessingException {
        this(previousHash, new LinkedList<>());
    }

    public Block(String previousHash, LinkedList<Transaction> transactions) throws JsonProcessingException {
        this.timeStamp = Calendar.getInstance().getTimeInMillis();
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.hash = calculateHash();
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
        data += "\"previousHash\": \"" + previousHash + "\","
                + "\"timeStamp\": " + Long.toString(timeStamp) + ","
                + "\"nonce\": " + Integer.toString(nonce) + ",";

        data += "\"transactions\":";
        data += mapper.writeValueAsString(transactions);
        return applySha256(data);
    }

    private String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Applies sha256 to our input,
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mineBlock(int difficulty) throws JsonProcessingException {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }


}
