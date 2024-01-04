package td.wallet.models;

import java.sql.Timestamp;
import java.time.Instant;

public class Balance {
    private final long id;
    private final Timestamp date;
    private final double amount;

    public Balance(int id, Timestamp date, double amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public Balance(){
        this.id = 0;
        this.date = null;
        this.amount = 0.0;
    }
    public Balance(Timestamp date, double amount) {
        this.id = 0;
        this.date = date;
        this.amount = amount;
    }

    public Balance(double amount) {
        this.id = 0;
        this.date = Timestamp.from(Instant.now());
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
