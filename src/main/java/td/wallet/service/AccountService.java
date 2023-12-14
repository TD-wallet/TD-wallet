package td.wallet.service;

import td.wallet.models.Account;
import td.wallet.models.Balance;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class AccountService {
    private final QueryTemplate qt = new QueryTemplate();

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
        return lastBalance.getAmount();
    }

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

}
