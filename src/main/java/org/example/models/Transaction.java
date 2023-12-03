package org.example.models;

import java.sql.Timestamp;

public class Transaction {
    private final int id;
    private final int amount;
    private final Timestamp transactionDate;
    private final TransactionType transactionType;

    public Transaction(int id, int amount, Timestamp transactionDate, TransactionType transactionType) {
        this.id = id;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }

    public Transaction(int amount, Timestamp transactionDate, TransactionType transactionType) {
        this.id = 0;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }
}
