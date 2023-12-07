package org.example.models;

import java.sql.Timestamp;

public class Transaction {
    private final int id;
    private final double amount;
    private final Timestamp date;
    private final TransactionType type;

    public Transaction(int id, double amount, Timestamp date, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public Transaction(double amount, Timestamp transactionDate, TransactionType transactionType) {
        this.id = 0;
        this.amount = amount;
        this.date = transactionDate;
        this.type = transactionType;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", transactionDate=" + date +
                ", transactionType=" + type +
                '}';
    }
}
