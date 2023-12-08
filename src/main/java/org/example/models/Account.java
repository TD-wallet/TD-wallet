package org.example.models;

import java.util.List;

public class Account {
    private final int id;
    private String ref;
    private double balance;
    private String type = "CASH";
    private Currency currency;
    private User user;
    private List<Transaction> transactions;

    public Account(int id, String accountNumber, double balance) {
        this.id = id;
        this.ref = accountNumber;
        this.balance = balance;
    }

    public Account(String accountNumber) {
        this.id = 0;
        this.ref = accountNumber;
        this.balance = 0;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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
