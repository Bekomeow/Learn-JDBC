package com.beko.jdbc.starter;

import com.beko.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException{
        Class<Driver> driverClass = Driver.class;
        String query = """
            INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_title, salary, department) VALUES
            (?, ?, ?, ?, ?, ?, ?, ?);
        """;
        try (var connection = ConnectionManager.open();
            var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "Bekzhan");
            preparedStatement.setString(2, "Toktamyssov");
            preparedStatement.setString(3, "gabitcool31@gmail.com");
            preparedStatement.setString(4, "87714931451");
            preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
            preparedStatement.setString(6, "Backend developer");
            preparedStatement.setBigDecimal(7, new BigDecimal(550000));
            preparedStatement.setString(8, "Engineering");

            System.out.println(preparedStatement);

            System.out.println(preparedStatement.executeUpdate());

        }
    }
}
