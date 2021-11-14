package com.haulmont.testtask.controller;

import org.hsqldb.jdbc.JDBCDataSource;

import java.io.*;
import java.sql.*;

public class DBManager {
    //private final String DBSCRIPT_PATH = "src/main/resources/createDatabase.sql";
    private final String DB_NAME = "taskdb";
    private final String DB_PATH = "src/main/resources/" + DB_NAME;
    private final String DB_URL = "jdbc:hsqldb:file:" + DB_PATH;

    private Connection connection = null;

    public Connection getConnection() {
        return this.connection;
    }

    public DBManager() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver"); //проверка драйвера
            System.out.println("driver is checked");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // установка значений
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setDatabaseName(DB_NAME);
        dataSource.setURL(DB_URL);
        dataSource.setUser("User");
        dataSource.setPassword("");

        // соединение
        try {
            connection = dataSource.getConnection();
            System.out.println("connect complete");
            //executeSqlScript("src/main/resources/createDatabase.sql");
            //System.out.println("script execute success");
            //executeSqlScript("src/main/resources/fillDatabase.sql");
            //System.out.println("fill complete");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void executeSqlScript(String path){
        InputStream input;
        try {
            input = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String str = reader.readLine();
            StringBuilder script = new StringBuilder(str);
            while ((str = reader.readLine()) != null) {
                script.append(str);
            }
            String[] statements = script.toString().split(";");

            for (String s : statements) {
                PreparedStatement statement = connection.prepareStatement(s);
                statement.execute();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}

