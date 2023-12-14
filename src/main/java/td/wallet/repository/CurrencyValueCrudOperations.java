package td.wallet.repository;

import td.wallet.models.Currency;
import td.wallet.models.CurrencyValue;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CurrencyValueCrudOperations implements CrudOperations<CurrencyValue> {
    private final QueryTemplate qt = new QueryTemplate();
    private final CurrencyCrudOperations currencyRepo = new CurrencyCrudOperations();


    @Override
    public CurrencyValue findById(long id) {
        return qt.executeSingleQuery(
                "SELECT * FROM currency_value WHERE id=?",
                ps -> ps.setLong(1, id),
                this::getResult
        );
    }

    @Override
    public List<CurrencyValue> findAll() {
        return qt.executeQuery(
                "SELECT * FROM currency_value ORDER BY id DESC",
                this::getResult
        );
    }

    @Override
    public List<CurrencyValue> saveAll(List<CurrencyValue> toSave, List<Integer> relId) {
        return saveAll(toSave);
    }

    public List<CurrencyValue> saveAll(List<CurrencyValue> toSave) {
        ArrayList<CurrencyValue> toBeSaved = new ArrayList<>();
        for (CurrencyValue currency: toSave) {
            CurrencyValue saving = this.save(currency);
            if (saving != null) {
                toBeSaved.add(saving);
            } else {
                return null;
            }
        }
        return toBeSaved;
    }

    @Override
    public CurrencyValue save(CurrencyValue toSave, long relId) {
        return save(toSave);
    }

    public CurrencyValue save(CurrencyValue toSave) {
        if(toSave.getId() == 0 && isSaved(toSave)) {
            return findAll().get(0);
        } else if (this.findById(toSave.getId()) != null) {
            return qt.executeUpdate(
                    "UPDATE currency SET amount=? WHERE id=?",
                    ps -> {
                        ps.setDouble(1, toSave.getAmount());
                        ps.setLong(2, toSave.getId());
                    }
            ) == 0 ? null : this.findById(toSave.getId());
        }
        return null;
    }

    @Override
    public CurrencyValue delete(CurrencyValue toDelete) {
        return qt.executeUpdate(
                "DELETE FROM currency_value WHERE id=?",
                ps -> {
                    ps.setLong(1, toDelete.getId());
                }
        ) == 0 ? null : toDelete;
    }

    public CurrencyValue findCurrentValue(Currency source, Currency destination) {
        return qt.executeSingleQuery(
                "SELECT * FROM currency_value WHERE id_source_currency=? AND id_destination_currency=?" +
                        " ORDER BY date DESC LIMIT 1",
                ps -> {
                    ps.setInt(1, source.getId());
                    ps.setInt(2, destination.getId());
                },
                this::getResult
        );
    }

    private CurrencyValue getResult(ResultSet rs) throws SQLException {
        return new CurrencyValue(
                rs.getLong(Columns.ID),
                currencyRepo.findById(rs.getInt(Columns.ID_SOURCE_CURR)),
                currencyRepo.findById(rs.getInt(Columns.ID_DEST_CURR)),
                rs.getDouble(Columns.AMOUNT),
                rs.getTimestamp(Columns.DATE)
        );
    }

    private boolean isSaved(CurrencyValue toSave) {
        return qt.executeUpdate(
                "INSERT INTO currency_value(id_source_currency, id_destination_currency, amount, date) " +
                        "VALUES (?,?,?,?)",
                ps -> {
                    ps.setInt(1, toSave.getSource().getId());
                    ps.setInt(2, toSave.getDestination().getId());
                    ps.setDouble(3, toSave.getAmount());
                    ps.setTimestamp(4, toSave.getDate() == null ?
                            Timestamp.from(Instant.now()) : toSave.getDate()
                    );
                }
        ) == 0;
    }
}
