package org.example.repository;

import org.example.models.Account;
import org.example.models.Balance;
import org.example.models.Transaction;
import org.example.models.TransactionType;
import org.example.utils.QueryTemplate;

public class TransactionActions {
    private QueryTemplate qt = new QueryTemplate();
    private TransactionCrudOperations transactionRepo = new TransactionCrudOperations();
    private AccountCrudOperations accountRepo = new AccountCrudOperations();
    private BalanceCrudOperations balanceRepo = new BalanceCrudOperations();

    public Account debit(Account account, double amount, String label) {
        Account acToDebit = accountRepo.findById(account.getId());
        if (acToDebit == null) {
            return null;
        } else {
            double oldBalance = acToDebit.getBalance().isEmpty() ? 0 : acToDebit.getBalance().get(0).getAmount();
            if (acToDebit.getType().equals("BANK") && oldBalance - amount < 0) return null;
            double newBalance = oldBalance + amount;
            balanceRepo.save(new Balance(newBalance), acToDebit.getId());
            transactionRepo.save(new Transaction(
                    amount,
                    label,
                    TransactionType.DEBIT
            ), acToDebit.getId());
        }

        return accountRepo.save(acToDebit, qt.executeSingleQuery(
                "SELECT id_user FROM account WHERE id=?",
                ps -> ps.setInt(1, acToDebit.getId()),
                rs -> rs.getInt("id_user")
        ));
    }

    public Account credit(Account account, double amount, String label) {
        Account toCreditAccount = accountRepo.findById(account.getId());

        if (toCreditAccount == null) return null;
        double oldBalance = toCreditAccount.getBalance().isEmpty() ? 0 : toCreditAccount.getBalance().get(0).getAmount();
        double newBalance = oldBalance + amount;
        balanceRepo.save(new Balance(newBalance), toCreditAccount.getId());
        transactionRepo.save(new Transaction(
                        amount,
                        label,
                        TransactionType.CREDIT
                ), toCreditAccount.getId()
        );
p
        return accountRepo.save(toCreditAccount, qt.executeSingleQuery(
                        "SELECT id_user FROM account WHERE id=?",
                        ps -> ps.setInt(1, toCreditAccount.getId()),
                        rs -> rs.getInt("id_user")
                )
        );
    }
}
