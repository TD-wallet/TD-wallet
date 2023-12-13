package td.wallet.repository;

import td.wallet.models.Currency;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyCrudOperations {
    private QueryTemplate qt = new QueryTemplate();

    public Currency findById(Integer id) {
        return qt.executeSingleQuery(
                "SELECT * FROM currency WHERE id=?",
                ps -> {
                    ps.setInt(1, id);
                },
                this::getResult
        );
    }

    public List<Currency> findAll() {
        return qt.executeQuery("SELECT * FROM currency ORDER BY id DESC",
                this::getResult
        );
    }

    public List<Currency> saveAll(List<Currency> toSave) {
        ArrayList<Currency> toBeSaved = new ArrayList<>();
        for (Currency currency: toSave) {
            Currency saving = this.save(currency);
            if (saving != null) {
                toBeSaved.add(saving);
            } else {
                return null;
            }
        }
        return toBeSaved;
    }

    public Currency save(Currency toSave) {
        if(toSave.getId() == 0 && isSaved(toSave)) {
                return findAll().get(0);
        } else if (this.findById(toSave.getId()) != null) {
            return qt.executeUpdate(
                    "UPDATE currency SET code=?, name=?, symbol=? WHERE id=?",
                    ps -> {
                        ps.setString(1, toSave.getCode());
                        ps.setString(2, toSave.getName());
                        ps.setString(3, toSave.getSymbol());
                        ps.setInt(4, toSave.getId());
                    }
            ) == 0 ? null : this.findById(toSave.getId());
        }
        return null;
    }

    public Currency delete(Currency toDelete) {
        return null;
    }

    private Currency getResult(ResultSet rs) throws SQLException {
        return new Currency(
                rs.getInt(Columns.ID),
                rs.getString(Columns.CODE),
                rs.getString(Columns.NAME),
                rs.getString(Columns.SYMBOL)
        );
    }

    private Boolean isSaved(Currency currency) {
        return qt.executeUpdate(
                "INSERT INTO currency (code, name, symbol) VALUES (?,?,?)",
                ps -> {
                    ps.setString(1, currency.getCode());
                    ps.setString(2, currency.getName());
                    ps.setString(3, currency.getSymbol());
                }
        ) != 0;
    }
}
