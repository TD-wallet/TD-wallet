package td.wallet.dto;

public class CategorizedTransactionSum {
    private final String categoryName;
    private final double totalAmount;


    public CategorizedTransactionSum(String categoryName, double totalAmount) {
        this.categoryName = categoryName;
        this.totalAmount = totalAmount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
