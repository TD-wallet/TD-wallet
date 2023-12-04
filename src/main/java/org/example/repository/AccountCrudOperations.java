package org.example.repository;

import org.example.models.Account;
import org.example.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountCrudOperations implements CrudOperations<Account> {
    private final QueryTemplate qt = new QueryTemplate();

    @Override
    public Account findById(Integer id) {
        return qt.executeSingleQuery("SELECT * FROM bank_account WHERE id=?", ps -> {
                    ps.setInt(1, id);
                },
                this::getResult
        );
    }

    @Override
    public List<Account> findAll() {
        return qt.executeQuery("SELECT * FROM bank_account ORDER BY id DESC",
                this::getResult
        );
    }

    @Override
    public List<Account> saveAll(List<Account> toSave) {
        for (Account account: toSave) {
            if(isNotSaved(account)) {
                return null;
            }
        }
        return toSave;
    }

    @Override
    public Account save(Account toSave) {
        return isNotSaved(toSave) ? null : toSave;
    }

    @Override
    public Account delete(Account toDelete) {
        return qt.executeUpdate(
                "DELETE FROM bank_account WHERE id=?",
                ps -> {
                    ps.setInt(1, toDelete.getId());
                }
        ) == 0 ? null : toDelete;
    }

    private Account getResult(ResultSet rs) throws SQLException {
         return new Account(
                rs.getInt("id"),
                rs.getString("account_number"),
                rs.getInt("balance")
        );
    }

    private boolean isNotSaved(Account toSave) {
        return qt.executeUpdate("INSERT INTO bank_account (id, account_number, balance) VALUES (?,?,?)",
            ps -> {
                ps.setInt(1, this.findAll().get(0).getId()+1);
                ps.setString(2, toSave.getAccountNumber());
                ps.setInt(3, toSave.getBalance());
            }
        ) == 0;
    }
}
