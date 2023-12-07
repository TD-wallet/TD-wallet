package org.example.repository;

import org.example.models.Account;
import org.example.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountCrudOperations implements CrudOperations<Account> {
    private final QueryTemplate qt = new QueryTemplate();
    CurrencyCrudOperations currencyRepo = new CurrencyCrudOperations();
    TransactionCrudOperations transactionRepo = new TransactionCrudOperations();

    @Override
    public Account findById(Integer id) {
        return qt.executeSingleQuery("SELECT * FROM account WHERE id=?", ps -> ps.setInt(1, id),
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
    public List<Account> saveAll(List<Account> toSave) {
        ArrayList<Account> savedAccounts = new ArrayList<>();
        for (Account account : toSave) {
            Account savedAccount = save(account);
            if (savedAccount == null) {
                return null;
            } else {
                savedAccounts.add(savedAccount);
            }
        }
        return savedAccounts;
    }

    @Override
    public Account save(Account toSave) {
        if (toSave.getId() == 0) {
            return isSaved(toSave) ? null : findAll().get(0);
        } else if (this.findById(toSave.getId()) != null) {
            return qt.executeUpdate(
                    "UPDATE account SET ref=?, type=?, balance=? WHERE id=?",
                    ps -> {
                        ps.setString(1, toSave.getRef());
                        ps.setString(2, toSave.getType());
                        ps.setDouble(3, toSave.getBalance());
                        ps.setInt(4, toSave.getId());
                    }
            ) == 0 ? null : this.findById(toSave.getId());
        }
        return null;
    }

    @Override
    public Account delete(Account toDelete) {
        Account toBeDeleted = this.findById(toDelete.getId());
        return qt.executeUpdate(
                "DELETE FROM account WHERE id=?",
                ps -> ps.setInt(1, toDelete.getId())
        ) == 0 ? null : toBeDeleted;
    }

    private Account getResult(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt("id"),
                rs.getString("ref"),
                rs.getDouble("balance"),
                rs.getString("type"),
                currencyRepo.findById(rs.getInt("id_currency")),
                transactionRepo.findByAccountId(rs.getInt("id"))
                );
    }

    private boolean isSaved(Account toSave) {
        return qt.executeUpdate("INSERT INTO account (id, ref, balance) VALUES (?,?,?)",
                ps -> {
                    ps.setInt(1, this.findAll().get(0).getId() + 1);
                    ps.setString(2, toSave.getRef());
                    ps.setDouble(3, toSave.getBalance());
                }
        ) != 0;
    }

    public List<Account> findByUserId(int id) {
        return qt.executeQuery(
                "SELECT * FROM account WHERE id_user=? ORDER BY id DESC",
                ps -> ps.setInt(1, id),
                this::getResult
        );
    }
}
