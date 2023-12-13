package td.wallet.models;

import java.sql.Timestamp;

public class Transfer {
    private final int id;
    private final Account debited;
    private final Account credited;
    private final Transaction transaction;
    private final Timestamp date;

    public Transfer(int id, Account debited, Account credited, Transaction transaction, Timestamp date) {
        this.id = id;
        this.debited = debited;
        this.credited = credited;
        this.transaction = transaction;
        this.date = date;
    }

    public Transfer(Account debited, Account credited, Transaction transaction, Timestamp date) {
        this.id = 0;
        this.debited = debited;
        this.credited = credited;
        this.transaction = transaction;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Account getDebited() {
        return debited;
    }

    public Account getCredited() {
        return credited;
    }

    public Transaction getTransaction() {
        return transaction;
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
                ", transaction=" + transaction +
                ", date=" + date +
                '}';
    }
}