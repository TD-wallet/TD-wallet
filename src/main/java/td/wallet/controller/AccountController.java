package td.wallet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import td.wallet.models.Account;
import td.wallet.models.Balance;
import td.wallet.service.AccountService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable long id) {
        return accountService.findAccountById(id);
    }

    @GetMapping("/all")
    public List<Account> getListOfAccount() {
        return accountService.findAllAccount();
    }

    @GetMapping("/balance-from-date")
    public Double getAccountBalanceFromDate(@RequestParam long accountId, @RequestParam Timestamp date) {
        Account account = accountService.findAccountById(accountId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        return accountService.getBalanceAtDate(account, date);
    }

    @GetMapping("/balance/interval")
    public List<Balance> geAccountBalanceFromIntervalDate(@RequestParam long accountId, @RequestParam Timestamp start, @RequestParam Timestamp end) {
        Account account = accountService.findAccountById(accountId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        return accountService.getBalanceAtInterval(account, start, end);
    }

    @GetMapping("/user/{id}")
    public List<Account> getAccountByIdUser(@PathVariable(name = "id") long userId) {
        return accountService.findAccountByUserId(userId);
    }

    @GetMapping("/sum-of-balance")
    public Balance getSumOfBalance(@RequestParam long accountId) {
        Account account = accountService.findAccountById(accountId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        return accountService.getBalance2(account);
    }


    @PostMapping("/new")
    public Account postNewAccount(@RequestBody Account toSave, @RequestParam long id) {
        return accountService.saveSingleAccount(toSave, id);
    }

    @PostMapping("/news")
    public List<Account> postNewAccounts(@RequestBody List<Account> toSave, @RequestParam List<Integer> accountId) {
        if (toSave.size() != accountId.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The size of accounts and user IDs must match");
        }
        return accountService.saveMultipleAccount(toSave, accountId);
    }

    @DeleteMapping("/delete-account/{id}")
    public Account deleteAccount(@PathVariable long id) {
        return accountService.deleteOneAccount(id);
    }
}
