package org.example.models;

import java.util.List;

public class Account {
    private final int id;
    private String ref;
    private List<Balance> balance;
    private String type = "CASH";
    private Currency currency;
    private List<Transaction> transactions;

    public Account(int id, String accountNumber, List<Balance> balance) {
        this.id = id;
        this.ref = accountNumber;
        this.balance = balance;
    }

    public Account(String accountNumber) {
        this.id = 0;
        this.ref = accountNumber;
        this.balance = List.of();
    }

    public Account(int id, String ref, List<Balance> balance, String type, Currency currency, List<Transaction> transactions) {
        this.id = id;
        this.ref = ref;
        this.balance = balance;
        this.type = type;
        this.currency = currency;
        this.transactions = transactions;
    }

    public int getId() {
        return id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public List<Balance> getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                '}';
    }
}
