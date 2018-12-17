package com.g4.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class Transaction implements Serializable {
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

    @Override
    public String toString() {
        String transaction = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            transaction = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return transaction;
    }
}
