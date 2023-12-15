package td.wallet.service;

import td.wallet.dto.TransactionSum;
import td.wallet.dto.TransactionSumByCategory;
import td.wallet.models.*;
import td.wallet.repository.AccountCrudOperations;
import td.wallet.repository.BalanceCrudOperations;
import td.wallet.repository.TransactionCrudOperations;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.Timestamp;

public class TransactionService {
    private final QueryTemplate qt = new QueryTemplate();
    private final TransactionCrudOperations transactionRepo = new TransactionCrudOperations();
    private final AccountCrudOperations accountRepo = new AccountCrudOperations();
    private final BalanceCrudOperations balanceRepo = new BalanceCrudOperations();

    public Account debit(Account account, double amount, String label, Category category) {
        Account acToDebit = accountRepo.findById(account.getId());
        if (acToDebit == null) {
            return null;
        } else {
            double oldBalance = acToDebit.getBalance().isEmpty() ? 0 : acToDebit.getBalance().get(0).getAmount();
            if (acToDebit.getType().equals("BANK") && oldBalance - amount < 0) return null;
            double newBalance = oldBalance - amount;
            balanceRepo.save(new Balance(newBalance), acToDebit.getId());
            transactionRepo.save(new Transaction(
                    amount,
                    label,
                    category,
                    TransactionType.DEBIT
            ), acToDebit.getId());
        }

        return accountRepo.save(acToDebit, qt.executeSingleQuery(
                "SELECT id_user FROM account WHERE id=?",
                ps -> ps.setLong(1, acToDebit.getId()),
                rs -> rs.getLong(Columns.ID_USER)
        ));
    }

    public Account credit(Account account, double amount, String label, Category category) {
        return retrieve(account, amount, label, TransactionType.CREDIT, category);
    }

    public Account transfer(Account account, double amount, String label, Category category) {
        return retrieve(account, amount, label, TransactionType.TRANSFER, category);
    }

    private Account retrieve(Account account, double amount, String label, TransactionType type, Category category) {
        Account toCreditAccount = accountRepo.findById(account.getId());

        if (toCreditAccount == null) return null;
        double oldBalance = toCreditAccount.getBalance().isEmpty() ? 0 : toCreditAccount.getBalance().get(0).getAmount();
        double newBalance = oldBalance + amount;
        balanceRepo.save(new Balance(newBalance), toCreditAccount.getId());
        transactionRepo.save(new Transaction(
                        amount,
                        label,
                        category,
                        type
                ), toCreditAccount.getId()
        );

        return accountRepo.save(toCreditAccount, qt.executeSingleQuery(
                        "SELECT id_user FROM account WHERE id=?",
                        ps -> ps.setLong(1, toCreditAccount.getId()),
                        rs -> rs.getLong(Columns.ID_USER)
                )
        );
    }

    public TransactionSum getTransactionSum(Account account, Timestamp startDate, Timestamp endDate) {
        return qt.executeCall(
                "{call get_sum_of_transactions(?,?,?)}",
                ps -> {
                    ps.setLong(1, account.getId());
                    ps.setTimestamp(2, startDate);
                    ps.setTimestamp(3, endDate);
                },
                // See RowMapper functional interface to see how dynamic mapping works
                rs -> new TransactionSum(
                        rs.getDouble(Columns.ENTRY_SUM),
                        rs.getDouble(Columns.DEBIT_SUM)
                )
        ).get(0);
    }

    public TransactionSumByCategory getTransactionSumByCategory(Account account, Timestamp startDate, Timestamp endDate) {
        return qt.executeCall(
                "{call get_sum_of_transactions_for_each_category(?,?,?)}",
                ps -> {
                    ps.setLong(1, account.getId());
                    ps.setTimestamp(2, startDate);
                    ps.setTimestamp(3, endDate);
                },
                // See RowMapper functional interface to see how dynamic mapping works
                rs -> new TransactionSumByCategory(
                        rs.getString(Columns.CATEGORY_NAME),
                        rs.getDouble(Columns.TOTAL_AMOUNT)
                )
        ).get(0);
    }
}
