package com.haulmont.testtask.dao;

import com.haulmont.testtask.controller.DBManager;
import com.haulmont.testtask.model.Client;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements Dao<Client> {

    private DBManager dbManager;
    private final String TABLE_NAME = "Client";

    public ClientDao(DBManager dbManager) {
        setDBManager(dbManager);
    }

    @Override
    public void setDBManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void add(Client entity) {
        PreparedStatement statement;
        try {
            String sql = "INSERT INTO " + TABLE_NAME
                    + " (Name, PhoneNumber, Email, PassNumber) VALUES (?,?,?,?)";
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPhoneNumber());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPassNumber());
            statement.execute();
            System.out.println("Client " + entity.getId() + " added successfully");

        }
        catch (SQLException e) {
            System.out.println("Error when adding a client");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Client entity) {
        PreparedStatement statement;
        try {
            String sql = "UPDATE " + TABLE_NAME
                    + " SET Name=?, PhoneNumber=?, Email=?, PassNumber=? WHERE Client_ID=?";
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPhoneNumber());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPassNumber());
            statement.setLong(5, entity.getId());
            statement.execute();
            System.out.println("Client " + entity.getId() + " updated successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when updating a client");
            e.printStackTrace();
        }
    }

    @Override
    public int delete(long id) {
        PreparedStatement statement;
        try {
            String sql = "DELETE FROM " + TABLE_NAME
                    + " WHERE Client_ID = " + id;
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.execute();
            System.out.println("Client " + id + " deleted successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when deleting a client");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public List<Client> getAll() {
        PreparedStatement statement;
        List<Client> resultList = new ArrayList<Client>();
        try {
            statement = dbManager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                resultList.add(new Client(
                        resultSet.getLong("Client_ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getString("Email"),
                        resultSet.getString("PassNumber")
                ));
            }
            System.out.println("All clients selected successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when getting the list of clients");
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Client getById(long id) {
        PreparedStatement statement;
        Client result;
        try {
            statement = dbManager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME
                    + " WHERE Client_ID=" + id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            result = new Client(
                    resultSet.getLong("Client_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getString("Email"),
                    resultSet.getString("PassNumber")
            );
            System.out.println("Client" + id + " selected successfully");
            return result;
        }
        catch (SQLException e) {
            System.out.println("Error when getting the client by id");
            e.printStackTrace();
        }
        return null;
    }
}
