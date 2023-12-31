package td.wallet.models;

import java.util.List;
import java.util.Objects;

public class Account {
    private final long id;
    private final String name;
    private final String ref;
    private final List<Balance> balance;
    private String type = "CASH";
    private Currency currency;
    private List<Transaction> transactions;

    public Account(int id, String accountNumber, List<Balance> balance) {
        this.id = id;
        this.name = "Untitled";
        this.ref = accountNumber;
        this.balance = balance;
    }

    public Account(String accountNumber, Currency currency) {
        this.id = 0;
        this.name = "Untitled";
        this.ref = accountNumber;
        this.balance = List.of();
        this.currency = currency;
    }

    public Account(long id,String accountNumber) {
        this.id = id;
        this.name = "Untitled";
        this.ref = accountNumber;
        this.balance = List.of();
    }

    public Account(int id, String ref, List<Balance> balance, String type, Currency currency, List<Transaction> transactions) {
        this.id = id;
        this.name = "Untitled";
        this.ref = ref;
        this.balance = balance;
        this.type = type;
        this.currency = currency;
        this.transactions = transactions;
    }

    public Account(int id, String ref, List<Balance> balance, String type, Currency currency, List<Transaction> transactions, String name) {
        this.id = id;
        this.name = name;
        this.ref = ref;
        this.balance = balance;
        this.type = type;
        this.currency = currency;
        this.transactions = transactions;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getRef() {
        return ref;
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
                ", name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                ", currency=" + currency +
                ", transactions=" + transactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
