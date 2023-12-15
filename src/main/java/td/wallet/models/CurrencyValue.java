package td.wallet.models;

import java.sql.Timestamp;
import java.util.Objects;

public class CurrencyValue {
    private final long id;
    private final Currency source;
    private final Currency destination;
    private final double amount;
    private final Timestamp date;

    public CurrencyValue(Currency source, Currency destination, double amount, Timestamp date) {
        this.id = 0;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.date = date;
    }

    public CurrencyValue(long id, Currency source, Currency destination, double amount, Timestamp date) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public Currency getSource() {
        return source;
    }

    public Currency getDestination() {
        return destination;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyValue that = (CurrencyValue) o;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(source, that.source) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination, amount);
    }
}
