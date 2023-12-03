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
        return qt.executeSingleQuery("SELECT * FROM account WHERE id=?", ps -> {
                    ps.setInt(1, id);
                },
                this::getResult
        );
    }

    @Override
    public List<Account> findAll() {
        return qt.executeQuery("SELECT * FROM account",
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
                "DELETE FROM account WHERE id=?",
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
        return qt.executeUpdate("INSERT INTO account (account_number, balance) VALUES (?,?)",
            ps -> {
                ps.setString(1, toSave.getAccountNumber());
                ps.setInt(2, toSave.getBalance());
            }
        ) == 0;
    }
}