package td.wallet.repository;

import td.wallet.models.Account;
import td.wallet.models.Transfer;
import td.wallet.repository.utils.AccountTransferRole;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TransferCrudOperations {
    private final QueryTemplate qt = new QueryTemplate();
    private final AccountCrudOperations accountRepo = new AccountCrudOperations();

    public TransferCrudOperations() {
    }

    public Transfer findById(long id) {
        return qt.executeSingleQuery("SELECT * FROM transfer WHERE id=?", ps -> ps.setLong(1, id), this::getResult);
    }

    public List<Transfer> findAll() {
        return qt.executeQuery("SELECT * FROM transfer ORDER BY id DESC", this::getResult);
    }

    public List<Transfer> saveAll(List<Transfer> toSave) {
        ArrayList<Transfer> saved = new ArrayList<>();
        for (Transfer transfer : toSave) {
            Transfer value = save(transfer);
            if (value == null) return null;
            saved.add(value);
        }
        return saved;
    }

    public Transfer save(Transfer toSave) {
        if (toSave.getId() == 0) {
            return isSaved(toSave) ? findAll().get(0) : null;
        } else if (findById(toSave.getId()) != null) {
            return qt.executeUpdate(
                    "UPDATE transfer SET amount=? WHERE id=?",
                    ps -> {
                        ps.setDouble(1, toSave.getAmount());
                        ps.setLong(2, toSave.getId());
                    }
            ) == 0 ? null : findById(toSave.getId());
        }

        return null;
    }

    public Transfer delete(Transfer toDelete) {
        return null;
    }

    public List<Transfer> findByAccount(Account account, AccountTransferRole accountTransferRole) {
        Account actedAcc = accountRepo.findById(account.getId());

        if (actedAcc == null) return null;
        if (accountTransferRole == AccountTransferRole.CREDITED) {
            return qt.executeQuery(
                    "SELECT * FROM  transfer WHERE id_credited=? ORDER BY date DESC",
                    ps -> ps.setLong(1, actedAcc.getId()),
                    this::getResult
            );
        } else if (accountTransferRole == AccountTransferRole.DEBITED) {
            return qt.executeQuery(
                    "SELECT * FROM  transfer WHERE id_debited=? ORDER BY date DESC",
                    ps -> ps.setLong(1, actedAcc.getId()),
                    this::getResult
            );
        }
        return null;
    }

    private Transfer getResult(ResultSet rs) throws SQLException {
        return new Transfer(
                rs.getInt(Columns.ID),
                accountRepo.findById(rs.getInt(Columns.ID_CREDITED)),
                accountRepo.findById(rs.getInt(Columns.ID_DEBITED)),
                rs.getDouble(Columns.AMOUNT), rs.getTimestamp(Columns.DATE)
        );
    }

    private boolean isSaved(Transfer toSave) {
        return qt.executeUpdate("INSERT INTO transfer (id_debited, id_credited, amount, date) VALUES (?,?,?,?)", ps -> {
            ps.setLong(1, toSave.getCredited().getId());
            ps.setLong(2, toSave.getDebited().getId());
            ps.setDouble(3, toSave.getAmount());
            ps.setTimestamp(4, toSave.getDate() == null ? Timestamp.from(Instant.now()) : toSave.getDate());
        }) != 0;
    }
}
