package td.wallet;

import td.wallet.models.Account;
import td.wallet.models.Balance;
import td.wallet.models.Transfer;
import td.wallet.models.User;
import td.wallet.repository.*;
import td.wallet.service.AccountService;
import td.wallet.service.TransferService;

public class Main {
    public static void main(String[] args) {
        // Repositories
        UserCrudOperations userRepo = new UserCrudOperations();
        AccountCrudOperations accountRepo = new AccountCrudOperations();
        TransferCrudOperations transferRepo = new TransferCrudOperations();
        TransactionCrudOperations transactionRepo = new TransactionCrudOperations();
        CurrencyCrudOperations currencyRepo = new CurrencyCrudOperations();
        CategoryCrudOperations categoryRepo = new CategoryCrudOperations();

        // Services
        TransferService transferService = new TransferService();
        AccountService accountService = new AccountService();

        User debitedUser = userRepo.findById(3);
        Account debitedAccount = accountRepo.findByUser(debitedUser).get(0);
        User creditedUser = userRepo.findById(1);
        Account creditedAccount = accountRepo.findByUser(creditedUser).get(0);

        System.out.printf("Debited Account: %s\nCredited Account: %s\n", debitedAccount, creditedAccount);

        Transfer transfer = transferService.transfer(debitedAccount, creditedAccount, 1, categoryRepo.findById(10));

        System.out.println(transfer);

        Balance balance = accountService.getBalance2(creditedAccount);

        System.out.printf("Balance of credited Account: %s", balance);
    }
}