package td.wallet.dto;

public class TransactionSum {
    private final double entrySum;
    private final double debitSum;

    public TransactionSum(double entrySum, double debitSum) {
        this.entrySum = entrySum;
        this.debitSum = debitSum;
    }

    public double getEntrySum() {
        return entrySum;
    }

    public double getDebitSum() {
        return debitSum;
    }
}
