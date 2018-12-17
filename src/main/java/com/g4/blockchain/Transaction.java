package com.g4.blockchain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Transaction implements Serializable {

    private String sender;
    private String recipient;
    private char operator;
    private double firstOperand;
    private double secondOperand;
    private double answer;
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
    public Transaction(String sender, String recipient, char operator, double firstOperand, double secondOperand) {
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

    public char getOperator() {
        return operator;
    }

    public double getFirstOperand() {
        return firstOperand;
    }

    public double getSecondOperand() {
        return secondOperand;
    }

    public BigDecimal getResult() {
        return result;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void calculate() {
        Scanner scanObject = new Scanner(System.in);

        System.out.println("Enter first number: ");
        firstOperand = scanObject.nextInt();

        System.out.println("Enter second number: ");
        secondOperand = scanObject.nextInt();

        System.out.println("What operation: ");
        operator = scanObject.next().charAt(0);

        switch (operator){
            case '+': answer = firstOperand + secondOperand;
                break;
            case '-': answer = firstOperand - secondOperand;
                break;
            case '*': answer = firstOperand * secondOperand;
                break;
            case '/': answer = firstOperand / secondOperand;
                break;
        }
        System.out.println(firstOperand+" "+operator+" "+firstOperand+" "+answer);
    }

}
