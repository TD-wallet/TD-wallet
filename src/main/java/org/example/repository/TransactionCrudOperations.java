package org.example.repository;

import org.example.models.Transaction;
import org.example.models.TransactionType;
import org.example.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        ArrayList<Transaction> saved = new ArrayList<>();
        for (Transaction transaction : toSave) {
            Transaction value = save(transaction);
            if (value == null) {
                return null;
            }
            saved.add(value);
        }
        return saved;
    }

    @Override
    public Transaction save(Transaction toSave) {
        if (toSave.getId() == 0) {
            return isSaved(toSave) ? findAll().get(0) : null;
        } else if (findById(toSave.getId()) != null) {
            return qt.executeUpdate(
                    "UPDATE transaction SET amount=?, date=?, type=?::\"TRANSACTION_TYPE\" WHERE id=?",
                    ps -> {
                        ps.setDouble(1, toSave.getAmount());
                        ps.setTimestamp(2, toSave.getDate());
                        ps.setString(3, toSave.getType().toString());
                        ps.setInt(4, toSave.getId());
                    }
            ) == 0 ? null : findById(toSave.getId());
        }
        return null;
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
                rs.getDouble("amount"),
                rs.getTimestamp("date"),
                TransactionType.valueOf(
                        rs.getString("type").toUpperCase()
                )
        );
    }

    private boolean isSaved(Transaction toSave) {
        return qt.executeUpdate(
                "INSERT INTO transaction (id, amount, date, type) VALUES (?,?,?,?::\"TRANSACTION_TYPE\")",
                ps -> {
                    ps.setInt(1, this.findAll().get(0).getId() + 1);
                    ps.setDouble(2, toSave.getAmount());
                    ps.setTimestamp(3, toSave.getDate());
                    ps.setString(4, toSave.getType().toString());
                }
        ) != 0;
    }
}
