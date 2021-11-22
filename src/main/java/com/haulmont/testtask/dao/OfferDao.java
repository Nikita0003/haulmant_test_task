package com.haulmont.testtask.dao;

import com.haulmont.testtask.controller.DBManager;
import com.haulmont.testtask.model.Offer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfferDao implements Dao<Offer> {

    private DBManager dbManager;
    private final String TABLE_NAME = "Offer";

    public OfferDao(DBManager dbManager) {
        setDBManager(dbManager);
    }

    @Override
    public void setDBManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void add(Offer entity) {
        PreparedStatement statement;
        try {
            String sql = "INSERT INTO " + TABLE_NAME
                    + " (Client, Credit, TotalSum, DateOffer, CountPayment, NextDate, NextSum, BodySum, PercentSum) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.setLong(1, entity.getClient().getId());
            statement.setLong(2, entity.getCredit().getId());
            statement.setDouble(3, entity.getTotalSum());
            statement.setString(4, entity.getDateOffer().toString());
            statement.setInt(5, entity.getCountPayment());
            statement.setString(6, entity.getNextDate().toString());
            statement.setDouble(7, entity.getNextSum());
            statement.setDouble(8, entity.getBodySum());
            statement.setDouble(9, entity.getPercentSum());
            statement.execute();
            System.out.println("Offer " + entity.getId() + " added successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when adding a offer");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Offer entity) {
        PreparedStatement statement;
        try {
            String sql = "UPDATE " + TABLE_NAME
                    + " SET Client=?, Credit=?, TotalSum=?, DateOffer=?, CountPayment=?, " +
                    "NextDate=?, NextSum=?, BodySum=?, PercentSum=? WHERE Offer_ID=?";
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.setLong(1, entity.getClient().getId());
            statement.setLong(2, entity.getCredit().getId());
            statement.setDouble(3, entity.getTotalSum());
            statement.setString(4, entity.getDateOffer().toString());
            statement.setInt(5, entity.getCountPayment());
            statement.setString(6, entity.getNextDate().toString());
            statement.setDouble(7, entity.getNextSum());
            statement.setDouble(8, entity.getBodySum());
            statement.setDouble(9, entity.getPercentSum());
            statement.setLong(10, entity.getId());
            statement.execute();
            System.out.println("Offer " + entity.getId() + " updated successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when updating a offer");
            e.printStackTrace();
        }
    }

    @Override
    public int delete(long id) {
        PreparedStatement statement;
        try {
            String sql = "DELETE FROM " + TABLE_NAME
                    + " WHERE Offer_ID = " + id;
            statement = dbManager.getConnection().prepareStatement(sql);
            statement.execute();
            System.out.println("Offer " + id + " deleted successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when deleting a offer");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public List<Offer> getAll() {
        PreparedStatement statement;
        List<Offer> resultList = new ArrayList<Offer>();
        ClientDao clientDao = new ClientDao(dbManager);
        CreditDao creditDao = new CreditDao(dbManager);
        try {
            statement = dbManager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                resultList.add(new Offer(
                        resultSet.getLong("Offer_ID"),
                        clientDao.getById(resultSet.getLong("Client")),
                        creditDao.getById(resultSet.getLong("Credit")),
                        resultSet.getDouble("TotalSum"),
                        resultSet.getDate("DateOffer").toLocalDate(),
                        resultSet.getInt("CountPayment"),
                        resultSet.getDate("NextDate").toLocalDate(),
                        resultSet.getDouble("NextSum"),
                        resultSet.getDouble("BodySum"),
                        resultSet.getDouble("PercentSum")
                ));
            }
            System.out.println("All offers selected successfully");
        }
        catch (SQLException e) {
            System.out.println("Error when getting the list of offers");
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Offer getById(long id) {
        PreparedStatement statement;
        Offer result;
        ClientDao clientDao = new ClientDao(dbManager);
        CreditDao creditDao = new CreditDao(dbManager);
        try {
            statement = dbManager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME
                    + " WHERE Offer_ID=" + id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            result = new Offer(
                    resultSet.getLong("Offer_ID"),
                    clientDao.getById(resultSet.getLong("Client")),
                    creditDao.getById(resultSet.getLong("Credit")),
                    resultSet.getDouble("TotalSum"),
                    resultSet.getDate("DateOffer").toLocalDate(),
                    resultSet.getInt("CountPayment"),
                    resultSet.getDate("NextDate").toLocalDate(),
                    resultSet.getDouble("NextSum"),
                    resultSet.getDouble("BodySum"),
                    resultSet.getDouble("PercentSum")
            );
            System.out.println("Offer" + id + " selected successfully");
            return result;
        }
        catch (SQLException e) {
            System.out.println("Error when getting the offer by id");
            e.printStackTrace();
        }
        return null;
    }
}
