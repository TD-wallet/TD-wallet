package td.wallet.service;

import org.springframework.stereotype.Service;
import td.wallet.models.Account;
import td.wallet.models.Balance;
import td.wallet.models.CurrencyValue;
import td.wallet.models.Transfer;
import td.wallet.repository.AccountCrudOperations;
import td.wallet.repository.BalanceCrudOperations;
import td.wallet.repository.CurrencyValueCrudOperations;
import td.wallet.repository.TransferCrudOperations;
import td.wallet.repository.utils.AccountTransferRole;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class AccountService {
    private final QueryTemplate qt;
    private final CurrencyValueCrudOperations currencyValueRepo;
    private final TransferCrudOperations transferRepo;
    private final BalanceCrudOperations balanceRepo;
    private final AccountCrudOperations accountCrudOperations;

    public AccountService(QueryTemplate qt, CurrencyValueCrudOperations currencyValueRepo, TransferCrudOperations transferRepo, BalanceCrudOperations balanceRepo, AccountCrudOperations accountCrudOperations) {
        this.qt = qt;
        this.currencyValueRepo = currencyValueRepo;
        this.transferRepo = transferRepo;
        this.balanceRepo = balanceRepo;
        this.accountCrudOperations = accountCrudOperations;
    }

    public Double getBalanceAtDate(Account account, Timestamp date) {
        Balance lastBalance = qt.executeSingleQuery(
                "SELECT * FROM balance WHERE id_account=? AND date <= ? ORDER BY date DESC LIMIT 1",
                ps -> {
                    ps.setLong(1, account.getId());
                    ps.setTimestamp(2, date);
                },
                rs -> new Balance(
                        rs.getInt(Columns.ID),
                        rs.getTimestamp(Columns.DATE),
                        rs.getDouble(Columns.AMOUNT)
                )
        );
        return lastBalance == null ? 0 : lastBalance.getAmount();
    }

    @Deprecated
    public Double getBalance(Account account) {
        return getBalanceAtDate(account, Timestamp.from(Instant.now()));
    }

    public List<Balance> getBalanceAtInterval(Account account, Timestamp start, Timestamp end) {
        return qt.executeQuery(
                "SELECT * FROM balance WHERE id_account=? AND date BETWEEN ? AND ?",
                ps -> {
                    ps.setLong(1, account.getId());
                    ps.setTimestamp(2, start);
                    ps.setTimestamp(3, end);
                },
                rs -> new Balance(
                        rs.getInt(Columns.ID),
                        rs.getTimestamp(Columns.DATE),
                        rs.getDouble(Columns.AMOUNT)
                )
        );
    }

    public Account findAccountById(long id) {
        return accountCrudOperations.findById(id);
    }

    public List<Account> findAllAccount() {
        return accountCrudOperations.findAll();
    }

    public List<Account> findAccountByUserId(long id) {
        return accountCrudOperations.findByUserId(id);
    }

    public Account saveSingleAccount(Account account, long relId) {
        return accountCrudOperations.save(account, relId);
    }

    public List<Account> saveMultipleAccount(List<Account> toSave, List<Integer> relId) {
        return accountCrudOperations.saveAll(toSave, relId);
    }

    public Account deleteOneAccount(long id) {
        Account toDelete = accountCrudOperations.findById(id);
        return accountCrudOperations.delete(toDelete);
    }

    public Balance getBalance2(Account account) {
        double finalBalance = 0;
        finalBalance += Math.abs(
                qt.executeQuery(
                        "SELECT sum(amount) FROM transaction WHERE id_account=? AND type!='TRANSFER'::\"TRANSACTION_TYPE\" GROUP BY type",
                        ps -> ps.setLong(1, account.getId()),
                        rs -> rs.getDouble(Columns.AMOUNT)
                ).stream().reduce(0d, (a, b) -> a - b)
        );

        List<Transfer> transfers = transferRepo.findByAccount(account, AccountTransferRole.CREDITED);

        for (Transfer transfer : transfers) {
            CurrencyValue value = currencyValueRepo.findValue(
                    transfer.getDebited().getCurrency(),
                    transfer.getCredited().getCurrency(),
                    transfer.getDate()
            );

            if (value != null) {
                finalBalance += transfer.getAmount() * value.getAmount();
            }
        }

        return balanceRepo.save(
                new Balance(
                        finalBalance
                ),
                account.getId()
        );
    }
}
