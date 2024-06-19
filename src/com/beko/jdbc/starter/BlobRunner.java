package com.beko.jdbc.starter;

import com.beko.jdbc.starter.util.ConnectionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;

public class BlobRunner {
    public static void main(String[] args) throws SQLException, IOException {
        getImage();
    }

    private static void getImage() throws SQLException, IOException {
        //language=PostgreSQL
        var query = """
                SELECT image
                FROM images
                WHERE id = ?
                """;
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1);
            var resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                var bytes = resultSet.getBytes("image");
                Files.write(Path.of("resources", "new_meow.jpg"), bytes, StandardOpenOption.CREATE);
            }
        }
    }

    private static void saveImage() throws SQLException, IOException {
        String query = """
        INSERT INTO images(image) VALUES (?)
        """;
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBytes(1, Files.readAllBytes(Path.of("resources", "meow.jpg")));
            preparedStatement.executeUpdate();
        }
    }
}
