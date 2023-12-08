package org.example.repository;

import org.example.models.Account;
import org.example.models.Columns;
import org.example.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountCrudOperations implements CrudOperations<Account> {
    private final QueryTemplate qt = new QueryTemplate();
    private final BalanceCrudOperations balanceRepo = new BalanceCrudOperations();
    private final TransactionCrudOperations transactionRepo = new TransactionCrudOperations();
    private final CurrencyCrudOperations currencyRepo = new CurrencyCrudOperations();

    @Override
    public Account findById(Integer id) {
        return qt.executeSingleQuery("SELECT * FROM account WHERE id=?", ps -> {
                    ps.setInt(1, id);
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
            if(saved == null) return  null;
            savedAccount.add(saved);
        }
        return savedAccount;
    }

    @Override
    public Account save(Account toSave, int relId) {
        if(toSave.getId() == 0) {
            if(isSaved(toSave, relId)) {
                return findAll().get(0);
            }
            return null;
        } else if(findById(toSave.getId()) != null) {
            return qt.executeUpdate(
                    "UPDATE account SET name=? WHERE id=?",
                    ps -> {
                        ps.setString(1, toSave.getName());
                        ps.setInt(2, toSave.getId());
                    }
            ) == 0 ? null : findById(toSave.getId());
        }
        return null;
    }

    @Override
    public Account delete(Account toDelete) {
        return qt.executeUpdate(
                "DELETE FROM account WHERE id=?",
                ps -> {
                    ps.setInt(1, toDelete.getId());
                }
        ) == 0 ? null : toDelete;
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

    private boolean isSaved(Account toSave, int relId) {
        return qt.executeUpdate("INSERT INTO account (id, ref, id_user) VALUES (?,?,?)",
                ps -> {
                    ps.setInt(1, this.findAll().get(0).getId() + 1);
                    ps.setString(2, toSave.getRef());
                    ps.setDouble(3, relId);
                }
        ) != 0;
    }

    public List<Account> getByUserId(int userId) {
        return qt.executeQuery(
                "SELECT * FROM account WHERE id_user=?",
                ps -> ps.setInt(1, userId),
                this::getResult
        );
    }
}
