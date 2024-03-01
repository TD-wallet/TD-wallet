package td.wallet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import td.wallet.dto.CategorizedTransactionSum;
import td.wallet.dto.TransactionSum;
import td.wallet.models.Account;
import td.wallet.models.Category;
import td.wallet.models.Transaction;
import td.wallet.service.TransactionService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable long id) {
        return transactionService.findSingleTransaction(id);
    }

    @GetMapping("/account/{id}")
    public List<Transaction> getTransactionsByAccountId(@PathVariable(name = "id") int accountId) {
        return transactionService.findTransactionByAccountId(accountId);
    }

    @GetMapping("/all")
    public List<Transaction> getAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @GetMapping("/sum")
    public TransactionSum getTransactionSum(@RequestParam long accountId, @RequestParam Timestamp start, @RequestParam Timestamp end) {
        return transactionService.findTransactionSum(accountId, start, end);
    }

    @GetMapping("/sum/group-by-category")
    public List<CategorizedTransactionSum> getTransactionSumGroupByCategory(@RequestParam long accountId, @RequestParam Timestamp start, @RequestParam Timestamp end) {
        return transactionService.findTransactionSumByCategory(accountId, start, end);
    }

    @PostMapping("/new")
    public Transaction postNewTransaction(@RequestBody Transaction toSave, @RequestParam long accountId) {
        return transactionService.saveSingleTransaction(toSave, accountId);
    }

    @PostMapping("/news")
    public List<Transaction> postNewTransactions(@RequestBody List<Transaction> toSave,@RequestParam List<Integer> accountIds) {
        if (toSave.size() != accountIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The size of transactions and account IDs must match");
        }
        return transactionService.saveMultipleTransaction(toSave, accountIds);
    }

    @PostMapping("/credit")
    public Account postNewCredit(@RequestBody Account account, @RequestParam double amount, @RequestParam String label, @RequestParam long categoryId) {
        Category category = new Category(categoryId, "", "");
        return transactionService.credit(account, amount, label, category);
    }

    @PostMapping("/debit")
    public Account postNewDebit(@RequestBody Account account, @RequestParam double amount, @RequestParam String label, @RequestParam long categoryId) {
        Category category = new Category(categoryId, "", "");
        return transactionService.debit(account, amount, label, category);
    }

    @PostMapping("/transfer")
    public Account postNewTransfer(@RequestBody Account account, @RequestParam double amount, @RequestParam String label, @RequestParam long categoryId) {
        Category category = new Category(categoryId, "", "");
        return transactionService.transfer(account, amount, label, category);
    }

    @DeleteMapping("/delete/{id}")
    public Transaction deleteTransactionById(@PathVariable long id) {
        return transactionService.deleteTransaction(id);
    }
}
