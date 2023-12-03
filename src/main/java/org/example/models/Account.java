package org.example.models;

public class Account {
    private final int id;
    private String accountNumber;
    private int balance;

    public Account(int id, String accountNumber, int balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account(String accountNumber) {
        this.id = 0;
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public int getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
