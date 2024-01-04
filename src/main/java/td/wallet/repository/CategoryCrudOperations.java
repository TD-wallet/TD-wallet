package td.wallet.repository;

import td.wallet.models.Category;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryCrudOperations {

    private final QueryTemplate qt = new QueryTemplate();

    public Category findById(long id) {
        return qt.executeSingleQuery("SELECT * FROM category WHERE id=?", ps -> {
                    ps.setLong(1, id);
                },
                this::getResult);
    }

    public List<Category> findAll() {
        return qt.executeQuery("SELECT * FROM category",
                this::getResult);
    }

    public List<Category> saveAll(List<Category> toSave) {
        List<Category> toBeSaved = new ArrayList<>();
        for (Category category : toSave) {
            Category saving = this.save(category);
            if (saving == null) {
                return null;
            }
            toBeSaved.add(saving);
        }
        return toBeSaved;
    }

    public Category save(Category toSave) {
        if (toSave.getId() == 0 && isSaved(toSave)) {
            return findAll().get(0);
        } else if (this.findById(toSave.getId()) != null) {
            return qt.executeUpdate(
                    "UPDATE category SET id=?, name=?, display_name=? WHERE id=?",
                    ps -> {
                        ps.setLong(1, toSave.getId());
                        ps.setString(2, toSave.getName());
                        ps.setString(3, toSave.getDisplayName());
                        ps.setLong(4, toSave.getId());
                    }
            ) == 0 ? null : this.findById(toSave.getId());
        }
        return null;
    }

    public Category delete(Category toDelete) {
        return qt.executeUpdate(
                "DELETE FROM category WHERE id=?",
                ps -> {
                    ps.setLong(1, toDelete.getId());
                }
        ) == 0 ? null : toDelete;
    }

    private Category getResult(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt(Columns.ID),
                rs.getString(Columns.NAME),
                rs.getString(Columns.DISPLAY_NAME));
    }


    private Boolean isSaved(Category category) {
        return qt.executeUpdate(
                "INSERT INTO category ( name, display_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, category.getName());
                    ps.setString(2, category.getDisplayName());
                }
        ) != 0;
    }
}
