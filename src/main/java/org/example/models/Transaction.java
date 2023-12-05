package org.example.models;

import java.sql.Timestamp;

public class Transaction {
    private final int id;
    private final double amount;
    private final Timestamp transactionDate;
    private final TransactionType transactionType;

    public Transaction(int id, double amount, Timestamp transactionDate, TransactionType transactionType) {
        this.id = id;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }

    public Transaction(double amount, Timestamp transactionDate, TransactionType transactionType) {
        this.id = 0;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", transactionType=" + transactionType +
                '}';
    }
}
