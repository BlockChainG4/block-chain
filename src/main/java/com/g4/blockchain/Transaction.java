package com.g4.blockchain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Transaction implements Serializable {

    private String sender;
    private String recipient;
    private char operator;
    private BigDecimal firstOperand;
    private BigDecimal secondOperand;
    private BigDecimal result;
    private List<Block> blocks;

    //RETURNED TRANSACTION
    public Transaction(String sender, String recipient, BigDecimal result) {
        this.sender = sender;
        this.recipient = recipient;
        this.result = result;
//        calculateHash();
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public char getOperator() {
        return operator;
    }

    public BigDecimal getFirstOperand() {
        return firstOperand;
    }

    public BigDecimal getSecondOperand() {
        return secondOperand;
    }

    public BigDecimal getResult() {
        return result;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public BigDecimal calculate() {

        switch (operator){
            case '+': result = firstOperand.add(secondOperand);
                break;
            case '-': result = firstOperand.subtract(secondOperand);
                break;
            case '*': result = firstOperand.multiply(secondOperand);
                break;
            case '/': result = firstOperand.divide(secondOperand);
                break;
        }
        System.out.println(firstOperand+" "+operator+" "+firstOperand+" "+result);

        return result;
    }

}