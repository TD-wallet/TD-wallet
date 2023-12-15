package td.wallet;

import td.wallet.models.Transaction;
import td.wallet.models.TransactionType;
import td.wallet.repository.AccountCrudOperations;
import td.wallet.repository.TransactionCrudOperations;
import td.wallet.repository.UserCrudOperations;

import java.sql.Timestamp;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        AccountCrudOperations accountRepo = new AccountCrudOperations();
        Integer idToGet = 1;

        System.out.println("Preforming tests of account CRUD operations");

        System.out.printf(
                "   - getAll attempt in account attempt: %s%n",
                accountRepo.findAll()
        );

        System.out.printf("   - findById in account retrieving id=%d : %s%n",
                idToGet,
                accountRepo.findById(idToGet)
        );

        // Account toInsertAccount = new Account(4, "HIJ456", 0);
        if (accountRepo.findById(4) != null) {
            System.out.println("    - save account : account already exists");
        } else {
            // System.out.printf("   - save account : %s%n", accountRepo.save(toInsertAccount));
        }

        // Account toDeleteAccount = new Account(4, null, 0);
//        System.out.printf("   - deleting account id=%d : %s%n%n",
//                toDeleteAccount.getId(),
//                accountRepo.delete(toDeleteAccount)
//        );

        TransactionCrudOperations transactionRepo = new TransactionCrudOperations();

        System.out.println("Preforming tests of transaction CRUD operations");

        System.out.printf("   - findAll in transaction : %s%n",
                transactionRepo.findAll()
        );

        System.out.printf("   - findById in transaction retrieving id=%d : %s%n",
                idToGet,
                transactionRepo.findById(idToGet)
        );

//        Transaction toSaveTransaction = new Transaction(
//                30_000,
//                Timestamp.from(Instant.now()),
//                TransactionType.CREDIT
//        );

        if (transactionRepo.findById(4) != null) {
            System.out.println("    - save transaction : transaction already exists");
        } else {
            // System.out.printf("   - save transaction : %s%n", transactionRepo.save(toSaveTransaction));
        }

        Transaction toDeleteTransaction = new Transaction(4, 0, null, null, null);

        System.out.printf("   - deleting transaction id=4 : %s%n", transactionRepo.delete(toDeleteTransaction));

        UserCrudOperations userRepo = new UserCrudOperations();

        System.out.println("Performing tests of user R operation");

        System.out.printf("   - findAll in account : %s%n", userRepo.findAll());

    }
}