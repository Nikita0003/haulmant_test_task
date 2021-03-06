package com.haulmont.testtask.dao;

import com.haulmont.testtask.controller.DBManager;
import com.haulmont.testtask.model.Credit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditDao implements Dao<Credit>{

    private DBManager dbManager;
    private final String TABLE_NAME = "Credit";

    public CreditDao(DBManager dbManager) {
        setDBManager(dbManager);
    }

    @Override
    public void setDBManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void add(Credit entity) {
        PreparedStatement statement;
        try {
            String sql = "INSERT INTO " + TABLE_NAME
                    + " (Limit, Percent) VALUES (?, ?)";
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.setDouble(1, entity.getLimit());
            statement.setDouble(2, entity.getPercent());
            statement.execute();
            System.out.println("Credit " + entity.getId() + " added successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when adding a credit");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Credit entity) {
        PreparedStatement statement;
        try {
            String sql = "UPDATE " + TABLE_NAME
                    + " SET Limit=?, Percent=? WHERE Credit_ID=?";
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.setDouble(1, entity.getLimit());
            statement.setDouble(2, entity.getPercent());
            statement.setLong(3, entity.getId());
            statement.execute();
            System.out.println("Credit " + entity.getId() + " updated successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when updating a credit");
            e.printStackTrace();
        }
    }

    @Override
    public int delete(long id) {
        PreparedStatement statement;
        try {
            String sql = "DELETE FROM " + TABLE_NAME
                    + " WHERE Credit_ID = " + id;
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.execute();
            System.out.println("Credit " + id + " deleted successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when deleting a credit");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public List<Credit> getAll() {
        PreparedStatement statement;
        List<Credit> resultList = new ArrayList<Credit>();
        try {
            statement = dbManager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                resultList.add(new Credit(
                        resultSet.getLong("Credit_ID"),
                        resultSet.getDouble("Limit"),
                        resultSet.getDouble("Percent")
                ));
            }
            System.out.println("All credits selected successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when getting the list of credits");
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Credit getById(long id) {
        PreparedStatement statement;
        Credit result;
        try {
            statement = dbManager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME
                    + " WHERE Credit_ID=" + id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            result = new Credit(
                    resultSet.getLong("Credit_ID"),
                    resultSet.getDouble("Limit"),
                    resultSet.getDouble("Percent")
            );
            System.out.println("Credit" + id + " selected successfully");
            return result;
        }
        catch (SQLException e) {
            System.out.println("Error when getting the credit by id");
            e.printStackTrace();
        }
        return null;
    }
}
