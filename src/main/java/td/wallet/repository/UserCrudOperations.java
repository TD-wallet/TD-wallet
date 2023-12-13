package td.wallet.repository;

import td.wallet.models.User;
import td.wallet.repository.utils.Columns;
import td.wallet.utils.QueryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCrudOperations {
    private final QueryTemplate qt = new QueryTemplate();
    private final AccountCrudOperations accountRepo = new AccountCrudOperations();

    public User findById(Integer id) {
        return qt.executeSingleQuery(
                "SELECT * FROM \"user\" WHERE id=?",
                ps -> {
                    ps.setInt(1, id);
                },
                this::getUser
        );
    }

    public List<User> findAll() {
        return qt.executeQuery(
                "SELECT * FROM \"user\" ORDER BY id DESC",
                this::getUser
        );
    }

    public List<User> saveAll(List<User> toSave) {
        ArrayList<User> saved = new ArrayList<>();
        for(User user : toSave) {
            User value = save(user);
            if(value == null) {
                return null;
            }
            saved.add(value);
        }
        return saved;
    }

    public User save(User toSave) {
        if (toSave.getId() == 0) {
            return isSaved(toSave) ? findAll().get(0) : null;
        } else if (findById(toSave.getId()) != null) {
             return qt.executeUpdate(
                     "UPDATE \"user\" SET username=?, password=?, email=? WHERE id=?",
                     ps -> {
                         ps.setString(1, toSave.getUsername());
                         ps.setString(2, toSave.getEmail());
                         ps.setString(3, toSave.getPassword());
                         ps.setInt(4, toSave.getId());
                     }
             ) == 0 ? null : toSave;
        }
        return null;
    }

    public User delete(User toDelete) {
        User toBeDeleted = findById(toDelete.getId());
        if (toBeDeleted == null) {
            return null;
        } else {
            return qt.executeUpdate(
                    "DELETE FROM \"user\" WHERE id=?",
                    ps -> {
                        ps.setInt(1, toBeDeleted.getId());
                    }
            ) != 0 ? toBeDeleted : null;
        }
    }

    private Boolean isSaved(User toSave) {
        return qt.executeUpdate(
                "INSERT INTO \"user\" (username, email, password) VALUES (?,?,?)",
                ps -> {
                    ps.setString(1, toSave.getUsername());
                    ps.setString(2, toSave.getEmail());
                    ps.setString(3, toSave.getPassword());
                }
        ) != 0;
    }

    private User getUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt(Columns.ID),
                rs.getString(Columns.USERNAME),
                rs.getString(Columns.EMAIL),
                rs.getString(Columns.PASSWORD),
                accountRepo.getByUserId(rs.getInt(Columns.ID_USER))
        );
    }
}
