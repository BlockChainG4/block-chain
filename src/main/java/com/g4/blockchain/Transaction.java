package com.g4.blockchain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Transaction implements Serializable {

    private String sender;
    private String recipient;
//    private String operator;
//    private BigDecimal firstOperand;
//    private BigDecimal secondOperand;
    private BigDecimal result;
    private List<Block> blocks;

    //RETURNED TRANSACTION
    public Transaction(String sender, String recipient, BigDecimal result) {
        this.sender = sender;
        this.recipient = recipient;
        this.result = result;
//        calculateHash();
    }

    //FOR CALCULATION
//    public Transaction(String sender, String recipient, String operator, BigDecimal firstOperand, BigDecimal secondOperand) {
//        this.sender = sender;
//        this.recipient = recipient;
//        this.operator = operator;
//        this.firstOperand = firstOperand;
//        this.secondOperand = secondOperand;
//    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

//    public String getOperator() {
//        return operator;
//    }
//
//    public BigDecimal getFirstOperand() {
//        return firstOperand;
//    }
//
//    public BigDecimal getSecondOperand() {
//        return secondOperand;
//    }

    public BigDecimal getResult() {
        return result;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
