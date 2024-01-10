package td.wallet.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import td.wallet.models.User;
import td.wallet.utils.ConnectionProvider;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class UserCrudOperationsTest {
    private  UserCrudOperations userCrudOperations;

    @BeforeAll
    public static void setOriginalConnection() {
        Connection originalConnection = ConnectionProvider.getConnection();
    }
    @BeforeEach
    public void setUserCrudOperations(){
         userCrudOperations = new UserCrudOperations();
    }

    @Test
    public void testFindById(){
        User existinguser = userCrudOperations.findById(1);
        // Assert
        assertNotNull(existinguser);

    }
    @Test
    public void testFindAll(){
        List<User> userList = userCrudOperations.findAll();
        assertNotNull(userList);
        assertFalse(userList.isEmpty());
    }

    @Test
    public void testSave(){
        // Arrange
        User userToSave = new User(0, "newUser", "newUser@email.com", "newPassword");

        // Act
        User savedUser = userCrudOperations.save(userToSave);
        assertNotNull(savedUser);
        assertNotEquals(0, savedUser.getId());
    }

    @Test
    public  void testDelete(){
        User userToDelete = userCrudOperations.findById(4);
        User userDeleted = userCrudOperations.delete(userToDelete);

        assertNotNull(userDeleted);
        assertEquals(userToDelete.getId(),userDeleted.getId());
    }

}
