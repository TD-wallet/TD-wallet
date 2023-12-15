package td.wallet.repository;

import td.wallet.models.Balance;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BalanceCrudOperations implements CrudOperations<Balance> {
    private final QueryTemplate qt = new QueryTemplate();

    @Override
    public Balance findById(long id) {
        return qt.executeSingleQuery(
                "SELECT * FROM balance WHERE id=?",
                ps -> ps.setLong(1, id),
                this::getResult
        );
    }

    @Override
    public List<Balance> findAll() {
        return qt.executeQuery(
                "SELECT * FROM balance ORDER BY id DESC",
                this::getResult
        );
    }

    @Override
    public List<Balance> saveAll(List<Balance> toSave, List<Integer> relId) {
        ArrayList<Balance> savedBalances = new ArrayList<>();
        for (int i = 0; i < toSave.size(); i++) {
            Balance saved = this.save(toSave.get(i), relId.get(i));
            if (saved == null) return null;
            savedBalances.add(saved);
        }
        return savedBalances;
    }

    @Override
    public Balance save(Balance toSave, long relId) {
        if (toSave.getId() == 0) {
            return isSaved(toSave, relId) ? this.findAll().get(0) : null;
        } else if (this.findById(toSave.getId()) != null) {
            return qt.executeUpdate(
                    "UPDATE currency SET date=?, amount=? WHERE id=?",
                    ps -> {
                        ps.setTimestamp(1, toSave.getDate());
                        ps.setDouble(2, toSave.getAmount());
                        ps.setLong(3, toSave.getId());
                    }
            ) == 0 ? null : this.findById(toSave.getId());
        }
        return null;
    }

    @Override
    public Balance delete(Balance toDelete) {
        return null;
    }

    private Balance getResult(ResultSet rs) throws SQLException {
        return new Balance(
                rs.getInt(Columns.ID),
                rs.getTimestamp(Columns.DATE),
                rs.getDouble(Columns.AMOUNT)
        );
    }

    private Boolean isSaved(Balance toSave, long relId) {
        return qt.executeUpdate(
                "INSERT INTO balance (date, amount, id_account) VALUES (?,?,?)",
                ps -> {
                    ps.setTimestamp(1, toSave.getDate());
                    ps.setDouble(2, toSave.getAmount());
                    ps.setLong(3, relId);
                }
        ) != 0;
    }

    public List<Balance> findByAccountId(long accountId) {
        return qt.executeQuery(
                "SELECT * FROM balance WHERE id_account=? ORDER BY id DESC",
                ps -> ps.setLong(1, accountId),
                this::getResult
        );
    }
}
