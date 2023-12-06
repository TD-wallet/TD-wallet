package org.example.repository;

import org.example.models.Transaction;
import org.example.models.TransactionType;
import org.example.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TransactionCrudOperations implements CrudOperations<Transaction> {
    private final QueryTemplate qt = new QueryTemplate();

    @Override
    public Transaction findById(Integer id) {
        return qt.executeSingleQuery(
                "SELECT * FROM transaction WHERE id=?",
                ps -> {
                    ps.setInt(1, id);
                },
                this::getResult
        );
    }

    @Override
    public List<Transaction> findAll() {
        return qt.executeQuery(
                "SELECT * FROM transaction ORDER BY id DESC",
                this::getResult
        );
    }

    @Override
    public List<Transaction> saveAll(List<Transaction> toSave) {
        for (Transaction transaction : toSave) {
            if (isNotSaved(transaction)) {
                return null;
            }
        }
        return toSave;
    }

    @Override
    public Transaction save(Transaction toSave) {
        return isNotSaved(toSave) ? null : toSave;
    }

    @Override
    public Transaction delete(Transaction toDelete) {
        return qt.executeUpdate(
                "DELETE FROM transaction WHERE id=?",
                ps -> {
                    ps.setInt(1, toDelete.getId());
                }
        ) == 0 ? null : toDelete;
    }

    private Transaction getResult(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getInt("id"),
                rs.getInt("amount"),
                rs.getTimestamp("transaction_date"),
                TransactionType.valueOf(
                        rs.getString("transaction_type").toUpperCase()
                )
        );
    }

    private boolean isNotSaved(Transaction toSave) {
        return qt.executeUpdate(
                "INSERT INTO transaction (id, amount, transaction_date, transaction_type) VALUES (?,?,?,?)",
                ps -> {
                    ps.setInt(1, this.findAll().get(0).getId() + 1);
                    ps.setDouble(2, toSave.getAmount());
                    ps.setTimestamp(3, toSave.getDate());
                    ps.setString(4, toSave.getType().toString());
                }
        ) == 0;
    }
}
