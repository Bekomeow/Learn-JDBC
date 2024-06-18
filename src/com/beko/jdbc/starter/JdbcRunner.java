package com.beko.jdbc.starter;

import com.beko.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException{
        Class<Driver> driverClass = Driver.class;
        String query = """
            SELECT * FROM employees
        """;
        try (var connection = ConnectionManager.open();
            var statement = connection.createStatement()) {

            var executeResultSet = statement.executeQuery(query);

            while (executeResultSet.next()) {
                System.out.println(executeResultSet.getLong("employee_id"));
                System.out.println(executeResultSet.getString("first_name"));
                System.out.println(executeResultSet.getDate("hire_date"));
                System.out.println("------");
            }
        }
    }
}
