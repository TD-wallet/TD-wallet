package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private static Connection connection;

    public static Connection getConnection() {
        String username = System.getenv("PG_USERNAME");
        String password = System.getenv("PG_PASSWORD");
        String databaseUrl = System.getenv("PG_DATABASE_URL");
        if(connection == null) {
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