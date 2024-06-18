package com.beko.jdbc.starter;

import com.beko.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.math.BigDecimal;
import java.sql.*;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException{
        Class<Driver> driverClass = Driver.class;
        String query = """
            INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_title, salary, department) VALUES
            (?, ?, ?, ?, ?, ?, ?, ?);
        """;
        try (var connection = ConnectionManager.open()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                var catalog = catalogs.getString(1);
                var schemas = metaData.getSchemas();
                System.out.println("Catalog: " +  catalog);
                while (schemas.next()) {
                    var schema = schemas.getString("TABLE_SCHEM");
                    var tables = metaData.getTables(catalog, schema, "%", new String[] {"TABLE"});
                    if (schema.equals("public")) {
                        while (tables.next()) {
                            System.out.println(tables.getString("TABLE_NAME"));
                        }
                    }
                }
            }

        }
    }
}
