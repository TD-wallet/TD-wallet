package td.wallet.repository;

import org.springframework.stereotype.Repository;
import td.wallet.models.Account;
import td.wallet.models.User;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountCrudOperations implements CrudOperations<Account> {
    private final QueryTemplate qt;
    private final BalanceCrudOperations balanceRepo;
    private final TransactionCrudOperations transactionRepo;
    private final CurrencyCrudOperations currencyRepo;

    public AccountCrudOperations(QueryTemplate qt, BalanceCrudOperations balanceRepo, TransactionCrudOperations transactionRepo, CurrencyCrudOperations currencyRepo) {
        this.qt = qt;
        this.balanceRepo = balanceRepo;
        this.transactionRepo = transactionRepo;
        this.currencyRepo = currencyRepo;
    }

    @Override
    public Account findById(long id) {
        return qt.executeSingleQuery("SELECT * FROM account WHERE id=?", ps -> {
                    ps.setLong(1, id);
                },
                this::getResult
        );
    }

    @Override
    public List<Account> findAll() {
        return qt.executeQuery("SELECT * FROM account ORDER BY id DESC",
                this::getResult
        );
    }

    @Override
    public List<Account> saveAll(List<Account> toSave, List<Integer> relId) {
        ArrayList<Account> savedAccount = new ArrayList<>();
        for (int i = 0; i < toSave.size(); i++) {
            Account saved = save(toSave.get(i), relId.get(i));
            if (saved == null) return null;
            savedAccount.add(saved);
        }
        return savedAccount;
    }

    @Override
    public Account save(Account toSave, long relId) {
        if (toSave.getId() == 0) {
            if (isSaved(toSave, relId)) {
                return findAll().get(0);
            }
            return null;
        } else if (findById(toSave.getId()) != null) {
            return qt.executeUpdate(
                    "UPDATE account SET name=? WHERE id=?",
                    ps -> {
                        ps.setString(1, toSave.getName());
                        ps.setLong(2, toSave.getId());
                    }
            ) == 0 ? null : findById(toSave.getId());
        }
        return null;
    }

    @Override
    public Account delete(Account toDelete) {
        Account toBeDeleted = findById(toDelete.getId());

        if (toBeDeleted == null) {
            return null;
        } else {
            return qt.executeUpdate(
                    "DELETE FROM account WHERE id=?",
                    ps -> {
                        ps.setLong(1, toDelete.getId());
                    }
            ) == 0 ? null : toBeDeleted;
        }
    }

    private Account getResult(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt(Columns.ID),
                rs.getString(Columns.REF),
                balanceRepo.findByAccountId(rs.getInt(Columns.ID)),
                rs.getString(Columns.TYPE),
                currencyRepo.findById(rs.getInt(Columns.ID_CURRENCY)),
                transactionRepo.findByAccountId(rs.getInt(Columns.ID)),
                rs.getString(Columns.NAME)
        );
    }

    private boolean isSaved(Account toSave, long relId) {
        return qt.executeUpdate("INSERT INTO account (id, ref, name, id_user, type, id_currency) VALUES (?,?,?,?,?,?)",
                ps -> {
                    ps.setLong(1, this.findAll().get(0).getId() + 1);
                    ps.setString(2, toSave.getRef());
                    ps.setString(3, toSave.getName());
                    ps.setDouble(4, relId);
                    ps.setString(5, toSave.getType());
                    ps.setInt(6, toSave.getCurrency().getId());
                }
        ) != 0;
    }

    public List<Account> findByUserId(long userId) {
        return qt.executeQuery(
                "SELECT * FROM account WHERE id_user=?",
                ps -> ps.setLong(1, userId),
                this::getResult
        );
    }

    public List<Account> findByUser(User user) {
        return qt.executeQuery(
                "SELECT * FROM account WHERE id_user=?",
                ps -> ps.setLong(1, user.getId()),
                this::getResult
        );
    }
}
