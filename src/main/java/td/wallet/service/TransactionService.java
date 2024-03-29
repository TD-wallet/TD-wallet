package td.wallet.service;

import org.springframework.stereotype.Service;
import td.wallet.dto.CategorizedTransactionSum;
import td.wallet.dto.TransactionSum;
import td.wallet.models.*;
import td.wallet.repository.AccountCrudOperations;
import td.wallet.repository.BalanceCrudOperations;
import td.wallet.repository.TransactionCrudOperations;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionService {
    private final QueryTemplate qt;
    private final TransactionCrudOperations transactionRepo;
    private final AccountCrudOperations accountRepo;
    private final BalanceCrudOperations balanceRepo;

    public TransactionService(QueryTemplate qt, TransactionCrudOperations transactionRepo, AccountCrudOperations accountRepo, BalanceCrudOperations balanceRepo) {
        this.qt = qt;
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
        this.balanceRepo = balanceRepo;
    }

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

    public Transaction findSingleTransaction(long id) {
        return transactionRepo.findById(id);
    }

    public List<Transaction> findAllTransactions() {
        return transactionRepo.findAll();
    }

    public List<Transaction> findTransactionByAccountId(int id) {
        return transactionRepo.findByAccountId(id);
    }

    public TransactionSum findTransactionSum(long accountId, Timestamp startDate, Timestamp endDate) {
        Account account = accountRepo.findById(accountId);
        if(account == null) {
            throw new IllegalArgumentException("Failed to find account with id: "+accountId);
        }
        return transactionRepo.getTransactionSum(account, startDate, endDate);
    }

    public List<CategorizedTransactionSum> findTransactionSumByCategory(long accountId, Timestamp startDate, Timestamp endDate) {
        Account account = accountRepo.findById(accountId);
        if(account == null) {
            throw new IllegalArgumentException("Failed to find account with id: "+accountId);
        }
        return transactionRepo.getCategorizedTransaction(account, startDate, endDate);
    }

    public Transaction saveSingleTransaction(Transaction toSave, long accId) {
        return transactionRepo.save(toSave, accId);
    }

    public List<Transaction> saveMultipleTransaction(List<Transaction> toSave, List<Integer> accIds) {
        return transactionRepo.saveAll(toSave, accIds);
    }

    public Transaction deleteTransaction(long id) {
        Transaction toDelete = transactionRepo.findById(id);
        if (toDelete == null) {
            throw new IllegalArgumentException("Transaction not found for the provided id: " + id);
        }
        try {
            return transactionRepo.delete(toDelete);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete transaction with id: " + id, e);
        }
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

    public CategorizedTransactionSum getTransactionSumByCategory(Account account, Timestamp startDate, Timestamp endDate) {
        return qt.executeCall(
                "{call get_sum_of_transactions_for_each_category(?,?,?)}",
                ps -> {
                    ps.setLong(1, account.getId());
                    ps.setTimestamp(2, startDate);
                    ps.setTimestamp(3, endDate);
                },
                // See RowMapper functional interface to see how dynamic mapping works
                rs -> new CategorizedTransactionSum(
                        rs.getString(Columns.CATEGORY_NAME),
                        rs.getDouble(Columns.TOTAL_AMOUNT)
                )
        ).get(0);
    }
}
