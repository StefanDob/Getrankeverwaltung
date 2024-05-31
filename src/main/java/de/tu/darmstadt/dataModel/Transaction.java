package de.tu.darmstadt.dataModel;

import java.time.LocalDateTime;
import java.util.Date;

public class Transaction {

    private String sender;

    private String receiver;

    private double amount;

    private LocalDateTime transactionDate;

    public Transaction(String sender, String receiver, double amount, LocalDateTime transactionDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
