package com.haulmont.testtask.dao;

import com.haulmont.testtask.controller.DBManager;
import com.haulmont.testtask.model.Bank;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankDao implements Dao<Bank>{

    private DBManager dbManager;
    private final String TABLE_NAME = "Bank";

    public BankDao(DBManager dbManager) {
        setDBManager(dbManager);
    }

    @Override
    public void setDBManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void add(Bank entity) {
        PreparedStatement statement;
        try {
            String sql = "INSERT INTO " + TABLE_NAME
                    + " (Bank_ID, Client, Credit) VALUES (?, ?, ?)";
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.setLong(1, entity.getBankId());
            statement.setLong(2, entity.getClient().getId());
            statement.setLong(3, entity.getCredit().getId());
            statement.execute();
            System.out.println("Record added successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when adding a bank");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Bank entity) {
        PreparedStatement statement;
        try {
            String sql = "UPDATE " + TABLE_NAME
                    + " SET Client=?, Credit=?, Bank_ID=? WHERE Record_ID=?";
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.setLong(1, entity.getClient().getId());
            statement.setLong(2, entity.getCredit().getId());
            statement.setLong(3, entity.getBankId());
            statement.setLong(4, entity.getId());
            statement.execute();
            System.out.println("Record updated successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when updating a bank");
            e.printStackTrace();
        }
    }

    @Override
    public int delete(long id) {
        PreparedStatement statement;
        try {
            String sql = "DELETE FROM " + TABLE_NAME
                    + " WHERE Record_ID = " + id;
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.execute();
            System.out.println("Record deleted successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when deleting a bank");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public List<Bank> getAll() {
        PreparedStatement statement;
        List<Bank> resultList = new ArrayList<Bank>();
        ClientDao clientDao = new ClientDao(dbManager);
        CreditDao creditDao = new CreditDao(dbManager);
        try {
            statement = dbManager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                resultList.add(new Bank(
                        resultSet.getLong("Record_ID"),
                        resultSet.getLong("Bank_ID"),
                        clientDao.getById(resultSet.getLong("Client")),
                        creditDao.getById(resultSet.getLong("Credit"))
                ));
            }
            System.out.println("All banks selected successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when getting the list of banks");
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Bank getById(long id) {
        PreparedStatement statement;
        Bank result;
        ClientDao clientDao = new ClientDao(dbManager);
        CreditDao creditDao = new CreditDao(dbManager);
        try {
            statement = dbManager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME
                    + " WHERE Record_ID=" + id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            result = new Bank(
                    resultSet.getLong("Record_ID"),
                    resultSet.getLong("Bank_ID"),
                    clientDao.getById(resultSet.getLong("Client")),
                    creditDao.getById(resultSet.getLong("Credit"))
            );
            System.out.println("Record selected successfully");
            return result;
        }
        catch (SQLException e) {
            System.out.println("Error when receiving the bank by id");
            e.printStackTrace();
        }
        return null;
    }
}
