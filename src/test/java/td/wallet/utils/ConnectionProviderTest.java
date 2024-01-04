package td.wallet.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionProviderTest {

    @BeforeEach
    public void setOriginalConnection(){
        Connection originalConnection = ConnectionProvider.getConnection();
    }

    @AfterEach
    public void tearDownOriginalConnection() throws SQLException{
        Connection originalConnection = ConnectionProvider.getConnection();
        if (originalConnection != null && !originalConnection.isClosed()) {
            originalConnection.close();
        }
    }

    @Test
    public void testGetConnection(){
        // Act
        Connection connection = ConnectionProvider.getConnection();

        // Assert
        assertNotNull(connection);
    }
    @Test
    public void testReusingConnection(){
        Connection connection1 = ConnectionProvider.getConnection();
        Connection connection2 = ConnectionProvider.getConnection();

        //Assert
        assertSame(connection1,connection2);
    }

    @Test
    public void testConnectionClose() throws SQLException {
        // Arrange

        // Act
        Connection connection = ConnectionProvider.getConnection();
        connection.close();

        // Assert
        assertTrue(connection.isClosed());
    }
}
