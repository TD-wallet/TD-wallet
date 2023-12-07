package org.example.repository;

import org.example.models.Account;
import org.example.models.Transaction;
import org.example.models.TransactionType;
import org.example.utils.QueryTemplate;

public class TransactionActions {
    private QueryTemplate qt = new QueryTemplate();
    private TransactionCrudOperations transactionRepo = new TransactionCrudOperations();
    private AccountCrudOperations accountRepo = new AccountCrudOperations();

    public Account debit(Account account, double amount, String label) {
        Account acToDebit = accountRepo.findById(account.getId());
        if (acToDebit == null || (
                acToDebit.getType().equals("BANK") && (
                        acToDebit.getBalance() - amount
                ) < 0)
        ) {
            return null;
        } else {
            double newBalance = acToDebit.getBalance() - amount;
            acToDebit.setBalance(newBalance);
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
        double newBalance = toCreditAccount.getBalance() + amount;
        toCreditAccount.setBalance(newBalance);
        transactionRepo.save(new Transaction(
                        amount,
                        label,
                        TransactionType.CREDIT
                ), toCreditAccount.getId()
        );

        return accountRepo.save(toCreditAccount, qt.executeSingleQuery(
                        "SELECT id_user FROM account WHERE id=?",
                        ps -> ps.setInt(1, toCreditAccount.getId()),
                        rs -> rs.getInt("id_user")
                )
        );
    }
}
