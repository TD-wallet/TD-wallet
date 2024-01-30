package td.wallet.service;

import org.springframework.stereotype.Service;
import td.wallet.models.User;
import td.wallet.repository.UserCrudOperations;

import java.util.List;
@Service
public class UserService {
    private final UserCrudOperations userCrudOperations;

    public UserService(UserCrudOperations userCrudOperations) {
        this.userCrudOperations = userCrudOperations;
    }
    public User findUserBydId(long id) {
        return userCrudOperations.findById(id);
    }
    public List<User> findAllUser() {
        return userCrudOperations.findAll();
    }
    public User saveSingleUser(User user) {
        return userCrudOperations.save(user);
    }
    public List<User> saveMultipleUser(List<User> users) {
        return userCrudOperations.saveAll(users);
    }
    public User deleteUser(long id) {
        User toDelete = userCrudOperations.findById(id);
        return userCrudOperations.delete(toDelete);
    }
}
