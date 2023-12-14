package td.wallet.models;

import java.sql.Timestamp;

public class Transfer {
    private final long id;
    private final Account debited;
    private final Account credited;
    private final double amount;
    private final Timestamp date;

    public Transfer(long id, Account debited, Account credited, double amount, Timestamp date) {
        this.id = id;
        this.debited = debited;
        this.credited = credited;
        this.amount = amount;
        this.date = date;
    }

    public Transfer(Account debited, Account credited, double amount, Timestamp date) {
        this.id = 0;
        this.debited = debited;
        this.credited = credited;
        this.amount = amount;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public Account getDebited() {
        return debited;
    }

    public Account getCredited() {
        return credited;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", debited=" + debited +
                ", credited=" + credited +
                ", date=" + date +
                '}';
    }
}