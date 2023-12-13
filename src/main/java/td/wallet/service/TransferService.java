package td.wallet.service;

import td.wallet.models.Account;
import td.wallet.models.Transfer;
import td.wallet.repository.AccountCrudOperations;
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
    private final QueryTemplate qt = new QueryTemplate();

    public Transfer transfer(Account debited, Account credited, double amount) {
        Account toDebit = accountRepo.findById(debited.getId());
        Account toCredit = accountRepo.findById(credited.getId());

        if (isValidTransfer(debited, credited)) {
            try {
                connection.setAutoCommit(false);
                Account senderTrans = transactionService.debit(
                        toDebit,
                        amount,
                        "Transfer to " + toCredit.getRef()
                );
                Account receiverTrans = transactionService.credit(
                        toCredit,
                        amount,
                        "Transfer from " + toDebit.getRef()
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
}
