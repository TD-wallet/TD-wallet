package td.wallet.service;

import td.wallet.models.Account;
import td.wallet.models.Balance;
import td.wallet.models.Transaction;
import td.wallet.repository.AccountCrudOperations;
import td.wallet.repository.BalanceCrudOperations;
import td.wallet.repository.TransactionCrudOperations;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;
import td.wallet.models.TransactionType;

public class TransactionService {
    private final QueryTemplate qt = new QueryTemplate();
    private final TransactionCrudOperations transactionRepo = new TransactionCrudOperations();
    private final AccountCrudOperations accountRepo = new AccountCrudOperations();
    private final BalanceCrudOperations balanceRepo = new BalanceCrudOperations();

    public Account debit(Account account, double amount, String label) {
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
                    TransactionType.DEBIT
            ), acToDebit.getId());
        }

        return accountRepo.save(acToDebit, qt.executeSingleQuery(
                "SELECT id_user FROM account WHERE id=?",
                ps -> ps.setLong(1, acToDebit.getId()),
                rs -> rs.getLong(Columns.ID_USER)
        ));
    }

    public Account credit(Account account, double amount, String label) {
        return retrieve(account, amount, label, TransactionType.CREDIT);
    }

    public Account transfer(Account account, double amount, String label) {
        return retrieve(account, amount, label, TransactionType.TRANSFER);
    }

    private Account retrieve(Account account, double amount, String label, TransactionType type) {
        Account toCreditAccount = accountRepo.findById(account.getId());

        if (toCreditAccount == null) return null;
        double oldBalance = toCreditAccount.getBalance().isEmpty() ? 0 : toCreditAccount.getBalance().get(0).getAmount();
        double newBalance = oldBalance + amount;
        balanceRepo.save(new Balance(newBalance), toCreditAccount.getId());
        transactionRepo.save(new Transaction(
                        amount,
                        label,
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
}
