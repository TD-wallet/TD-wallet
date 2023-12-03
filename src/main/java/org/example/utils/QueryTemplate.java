package org.example.utils;

import org.example.utils.lambdas.PreparedStatementSetter;
import org.example.utils.lambdas.RowMapper;

import javax.management.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryTemplate {
    private final Connection connection = ConnectionProvider.getConnection();

    public QueryTemplate() {
    }

    public <T> List<T> executeQuery(String query, PreparedStatementSetter pss, RowMapper<T> rowMapper) {
        ArrayList<T> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            pss.setStatement(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public <T> List<T> executeQuery(String query, RowMapper<T> rowMapper) {
        ArrayList<T> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return result;
    }

    public <T> T executeSingleQuery(String query, RowMapper<T> rowMapper) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return rowMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public <T> T executeSingleQuery(String query, PreparedStatementSetter pss, RowMapper<T> rowMapper) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            pss.setStatement(statement);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return rowMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Integer executeUpdate(String query) {
        try {
            Statement statement =  connection
                    .createStatement();
            Integer affectedRows =
                    statement.executeUpdate(query);
            statement.close();
            return affectedRows;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        };

        return 0;
    }

    public Integer executeUpdate( String insertStatement, PreparedStatementSetter pss) {
        try {
            PreparedStatement ps = connection.prepareStatement(insertStatement);
            pss.setStatement(ps);
            Integer affectedRows = ps.executeUpdate();
            ps.close();
            return affectedRows;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }
}
