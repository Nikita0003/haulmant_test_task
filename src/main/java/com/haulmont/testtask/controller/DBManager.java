package com.haulmont.testtask.controller;

import org.hsqldb.jdbc.JDBCDataSource;
import java.sql.*;

public class DBManager {
    private final String DB_NAME = "taskdb";
    private final String DB_PATH = "src/main/resources/" + DB_NAME;
    private final String DB_URL = "jdbc:hsqldb:file:" + DB_PATH;
    private final JDBCDataSource dataSource;
    private Connection connection = null;

    public DBManager() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            System.out.println("driver is checked");
        } catch (ClassNotFoundException e) {
            System.out.println("Error jdbc driver");
            e.printStackTrace();
        }

        dataSource = new JDBCDataSource();
        dataSource.setDatabaseName(DB_NAME);
        dataSource.setURL(DB_URL);
        dataSource.setUser("User");
        dataSource.setPassword("");

        try {
            connection = dataSource.getConnection();
            System.out.println("connect complete");
        }
        catch (SQLException e){
            System.out.println("Error when connecting to the database");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}

