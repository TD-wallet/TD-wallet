package td.wallet.service;

import td.wallet.models.Account;
import td.wallet.models.Category;
import td.wallet.models.CurrencyValue;
import td.wallet.models.Transfer;
import td.wallet.repository.AccountCrudOperations;
import td.wallet.repository.CurrencyValueCrudOperations;
import td.wallet.repository.TransferCrudOperations;
import td.wallet.utils.ConnectionProvider;
import td.wallet.utils.QueryTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class TransferService {
    private final Connection connection = ConnectionProvider.getConnection();
    private final TransactionService transactionService = new TransactionService();
    private final TransferCrudOperations transferRepo = new TransferCrudOperations();
    private final AccountCrudOperations accountRepo = new AccountCrudOperations();
    private final CurrencyValueCrudOperations currencyValueRepo = new CurrencyValueCrudOperations();
    private final QueryTemplate qt = new QueryTemplate();

    public Transfer transfer(Account debited, Account credited, double amount, Category category) {
        Account toDebit = accountRepo.findById(debited.getId());
        Account toCredit = accountRepo.findById(credited.getId());

        if (isValidTransfer(debited, credited)) {
            try {
                connection.setAutoCommit(false);
                Account senderTrans = transactionService.debit(
                        toDebit,
                        amount,
                        "Transfer to " + toCredit.getRef(),
                        category
                );

                Account receiverTrans = transactionService.transfer(
                        toCredit,
                        getConvertedValue(toDebit, toCredit, amount),
                        "Transfer from " + toDebit.getRef(),
                        category
                );
                if (isValidTransfer(senderTrans, receiverTrans)) {
                    Transfer transfer = transferRepo.save(
                            new Transfer(
                                    senderTrans,
                                    receiverTrans,
                                    amount,
                                    Timestamp.from(Instant.now())
                            )
                    );
                    if (transfer != null) {
                        connection.commit();
                        connection.setAutoCommit(true);
                        return transfer;
                    } else {
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                }

            } catch (SQLException e) {
                System.err.println("Error while performing transaction");
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    private boolean isValidTransfer(Account acc1, Account acc2) {
        return acc1 != null && acc2 != null && !acc1.equals(acc2);
    }

    private double getConvertedValue(Account sender, Account receiver, double amount) throws SQLException {
        CurrencyValue value = currencyValueRepo.findCurrentValue(sender.getCurrency(), receiver.getCurrency());
        if (sender.getCurrency().equals(receiver.getCurrency())) return amount;
        else if (value == null) {
            throw new SQLException(
                    new Throwable(
                            "Current transfer not supported"
                    )
            );
        }

        return amount * value.getAmount();
    }
}
