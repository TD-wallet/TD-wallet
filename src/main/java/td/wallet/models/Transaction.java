package td.wallet.models;

import java.sql.Timestamp;
import java.time.Instant;

public class Transaction {
    private final long id;
    private final double amount;
    private final String label;
    private final Timestamp date;
    private final Category category;
    private final TransactionType type;

    public Transaction(int id, double amount, Timestamp date, Category category, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.label = "Default";
        this.date = date;
        this.type = type;
    }

    public Transaction(double amount, Timestamp transactionDate, Category category, TransactionType transactionType) {
        this.category = category;
        this.id = 0;
        this.amount = amount;
        this.label = "Default";
        this.date = transactionDate;
        this.type = transactionType;
    }

    public Transaction(double amount, Timestamp transactionDate, String label, Category category, TransactionType transactionType) {
        this.category = category;
        this.id = 0;
        this.amount = amount;
        this.label = label;
        this.date = transactionDate;
        this.type = transactionType;
    }

    public Transaction(long id, double amount, Timestamp transactionDate, String label, Category category, TransactionType transactionType) {
        this.id = id;
        this.amount = amount;
        this.label = label;
        this.date = transactionDate;
        this.category = category;
        this.type = transactionType;
    }

    public Transaction(double amount, String label, Category category, TransactionType transactionType) {
        this.category = category;
        this.id = 0;
        this.amount = amount;
        this.label = label;
        this.date = Timestamp.from(Instant.now());
        this.type = transactionType;
    }


    public String getLabel() {
        return label;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
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
