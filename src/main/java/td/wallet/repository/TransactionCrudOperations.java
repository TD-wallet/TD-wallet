package td.wallet.repository;

import td.wallet.models.Transaction;
import td.wallet.models.TransactionType;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudOperations implements CrudOperations<Transaction> {
    private final QueryTemplate qt = new QueryTemplate();
    private final CategoryCrudOperations categoryRepo = new CategoryCrudOperations();

    @Override
    public Transaction findById(long id) {
        return qt.executeSingleQuery(
                "SELECT * FROM transaction WHERE id=?",
                ps -> {
                    ps.setLong(1, id);
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
    public List<Transaction> saveAll(List<Transaction> toSave, List<Integer> accountIds) {
        ArrayList<Transaction> saved = new ArrayList<>();
        for (int i = 0; i < toSave.size(); i++) {
            Transaction transaction = toSave.get(i);
            Transaction value = save(transaction, accountIds.get(i));
            if (value == null) {
                return null;
            }
            saved.add(value);
        }
        return saved;
    }

    @Override
    public Transaction save(Transaction toSave, long accountId) {
        if (toSave.getId() == 0) {
            return isSaved(toSave, accountId) ? findAll().get(0) : null;
        } else if (findById(toSave.getId()) != null) {
            return qt.executeUpdate(
                    "UPDATE transaction SET amount=?, label=?, date=?, type=?::\"TRANSACTION_TYPE\" WHERE id=?",
                    ps -> {
                        ps.setDouble(1, toSave.getAmount());
                        ps.setString(2, toSave.getLabel());
                        ps.setTimestamp(3, toSave.getDate());
                        ps.setString(4, toSave.getType().toString());
                        ps.setLong(5, toSave.getId());
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
                    ps.setLong(1, toDelete.getId());
                }
        ) == 0 ? null : toDelete;
    }

    private Transaction getResult(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getInt(Columns.ID),
                rs.getDouble(Columns.AMOUNT),
                rs.getTimestamp(Columns.DATE),
                rs.getString(Columns.LABEL),
                categoryRepo.findById(rs.getLong(Columns.ID_CATEGORY)),
                TransactionType.valueOf(
                        rs.getString(Columns.TYPE).toUpperCase()
                )
        );
    }

    private boolean isSaved(Transaction toSave, long accountId) {
        return qt.executeUpdate(
                "INSERT INTO transaction (id, amount, label, date, type, id_account, id_category) " +
                        "VALUES (?,?,?,?,?::\"TRANSACTION_TYPE\",?, ?)",
                ps -> {
                    ps.setLong(1, this.findAll().get(0).getId() + 1);
                    ps.setDouble(2, toSave.getAmount());
                    ps.setString(3, toSave.getLabel());
                    ps.setTimestamp(4, toSave.getDate());
                    ps.setString(5, toSave.getType().toString());
                    ps.setLong(6, accountId);
                    ps.setLong(7, toSave.getCategory().getId());
                }
        ) != 0;
    }

    public List<Transaction> findByAccountId(int id) {
        return qt.executeQuery(
                "SELECT * FROM transaction WHERE id_account=? ORDER BY id DESC",
                ps -> ps.setInt(1, id),
                this::getResult
        );
    }
}
