package com.beko.jdbc.starter;

import com.beko.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        int parentId = 1;
        var deleteParent = "DELETE FROM parents WHERE parent_id = ?";
        var deleteChild = "DELETE FROM children WHERE child_id = ?";
        Connection connection = null;
        PreparedStatement deleteParentStatement = null;
        PreparedStatement deleteChildStatement = null;
        try {
//            1. Создание соединения
            connection = ConnectionManager.open();
            deleteParentStatement = connection.prepareStatement(deleteParent);
            deleteChildStatement = connection.prepareStatement(deleteChild);

//            2. Отключение автокоммита сразу после создания соединения
            connection.setAutoCommit(false);

            deleteParentStatement.setInt(1, parentId);
            deleteChildStatement.setInt(1, parentId);



//            3. Выполнение SQL-запросов
            deleteChildStatement.executeUpdate();

            deleteParentStatement.executeUpdate();

//            4. Фиксация транзакции
            connection.commit();
        } catch(Exception e) {
//            5. Обработка исключений и откат транзакции в случае ошибки
            if(connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
//            6. Закрытие ресурсов (в блоке finally)
            if(connection != null) {
                connection.close();
            }
            if(deleteParentStatement != null) {
                deleteParentStatement.close();
            }
            if(deleteChildStatement != null) {
                deleteChildStatement.close();
            }
        }
    }
}

