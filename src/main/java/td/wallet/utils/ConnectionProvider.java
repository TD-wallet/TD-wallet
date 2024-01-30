package td.wallet.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Configuration
public class ConnectionProvider {
    private Connection connection;

    @Bean
    public Connection getConnection() {
        String username = System.getenv("PG_USERNAME");
        String password = System.getenv("PG_PASSWORD");
        String databaseUrl = System.getenv("PG_DATABASE_URL");
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        String.format("jdbc:postgresql://%s", databaseUrl),
                        username,
                        password
                );
            } catch (SQLException e) {
                System.out.println("WARNING: there is an error while providing connection");
                System.out.println(e.getMessage());
            }
        }

        return connection;
    }
}