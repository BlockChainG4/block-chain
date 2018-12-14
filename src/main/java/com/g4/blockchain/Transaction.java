package com.g4.blockchain;

import java.util.List;

public class Transaction {

    private String sender;
    private String recipient;
    private String operator;
    private long firstOperand;
    private long secondOperand;
    private long result;
    private List<Block> blocks;

    public Transaction(String sender, String recipient, String operator, long firstOperand, long secondOperand) {
        this.sender = sender;
        this.recipient = recipient;
        this.operator = operator;
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getOperator() {
        return operator;
    }

    public long getFirstOperand() {
        return firstOperand;
    }

    public long getSecondOperand() {
        return secondOperand;
    }

    public long getResult() {
        return result;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
