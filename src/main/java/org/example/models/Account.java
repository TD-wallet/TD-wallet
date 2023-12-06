package org.example.models;

import java.util.List;
import java.util.Locale;

public class Account {
    private final int id;
    private String ref;
    private double balance;
    private String type = "CASH";
    private final Currency currency;
    private final List<Transaction> transactions;

    public Account(int id, String accountNumber, double balance) {
        this.id = id;
        this.ref = accountNumber;
        this.balance = balance;
        this.currency = new Currency(
                Locale.getDefault().getDisplayName(),
                Locale.getDefault().getISO3Country(),
                java.util.Currency.getInstance(
                        Locale.getDefault()
                ).getSymbol()
        );
        this.transactions = List.of();
    }

    public Account(String accountNumber, User user) {
        this.id = 0;
        this.ref = accountNumber;
        this.balance = 0;
        this.currency = new Currency(
                Locale.getDefault().getDisplayName(),
                Locale.getDefault().getISO3Country(),
                java.util.Currency.getInstance(
                        Locale.getDefault()
                ).getSymbol()
        );
        this.transactions = List.of();
    }

    public Account(String ref, double balance, String type, Currency currency, List<Transaction> transactions) {
        this.id = 0;
        this.ref = ref;
        this.balance = balance;
        this.type = type;
        this.currency = currency;
        this.transactions = transactions;
    }

    public Account(int id, String ref, double balance, String type, Currency currency, List<Transaction> transactions) {
        this.id = id;
        this.ref = ref;
        this.balance = balance;
        this.type = type;
        this.currency = currency;
        this.transactions = transactions;
    }

    public String getType() {
        return type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<Transaction> getTransactions() {
        return transactions;
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
                ", currency=" + currency +
                ", transactions=" + transactions +
                '}';
    }
}
