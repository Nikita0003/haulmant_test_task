package com.haulmont.testtask.controller;

import com.haulmont.testtask.dao.*;
import com.haulmont.testtask.model.*;

import java.sql.SQLException;
import java.util.List;

public class Controller {
    private static DBManager dbManager;
    private static ClientDao clientDao;
    private static CreditDao creditDao;
    private static BankDao bankDao;
    private static OfferDao offerDao;

    private static void InitialConnection() {
        dbManager = new DBManager();
        clientDao = new ClientDao(dbManager);
        creditDao = new CreditDao(dbManager);
        bankDao = new BankDao(dbManager);
        offerDao = new OfferDao(dbManager);
    }

    private static void CloseConnection() {
        try {
            dbManager.getConnection().close();
        } catch (SQLException e) {
            System.out.println("Error closing connection");
            e.printStackTrace();
        }
    }

    public static void addClient(Client client) {
        InitialConnection();
        clientDao.add(client);
        CloseConnection();
    }

    public static void updateClient(Client client) {
        InitialConnection();
        clientDao.update(client);
        CloseConnection();
    }

    public static int deleteClient(long id) {
        InitialConnection();
        int result = clientDao.delete(id);
        CloseConnection();
        return result;
    }

    public static Client getClientById(long id) {
        InitialConnection();
        Client result = clientDao.getById(id);
        CloseConnection();
        return result;
    }

    public static List<Client> getClientList() {
        InitialConnection();
        List<Client> result = clientDao.getAll();
        CloseConnection();
        return result;
    }

    public static void addCredit(Credit credit) {
        InitialConnection();
        creditDao.add(credit);
        CloseConnection();
    }

    public static void updateCredit(Credit credit) {
        InitialConnection();
        creditDao.update(credit);
        CloseConnection();
    }

    public static int deleteCredit(long id) {
        InitialConnection();
        int result = creditDao.delete(id);
        CloseConnection();
        return result;
    }

    public static Credit getCreditById(long id) {
        InitialConnection();
        Credit result = creditDao.getById(id);
        CloseConnection();
        return result;
    }

    public static List<Credit> getCreditList() {
        InitialConnection();
        List<Credit> result = creditDao.getAll();
        CloseConnection();
        return result;
    }

    public static void addBank(Bank bank) {
        InitialConnection();
        bankDao.add(bank);
        CloseConnection();
    }

    public static void updateBank(Bank bank) {
        InitialConnection();
        bankDao.update(bank);
        CloseConnection();
    }

    public static int deleteBank(long id) {
        InitialConnection();
        int result = bankDao.delete(id);
        CloseConnection();
        return result;
    }

    public static Bank getBankById(long id) {
        InitialConnection();
        Bank result = bankDao.getById(id);
        CloseConnection();
        return result;
    }

    public static List<Bank> getBankList() {
        InitialConnection();
        List<Bank> result = bankDao.getAll();
        CloseConnection();
        return result;
    }

    public static void addOffer(Offer offer) {
        InitialConnection();
        offerDao.add(offer);
        CloseConnection();
    }

    public static void updateOffer(Offer offer) {
        InitialConnection();
        offerDao.update(offer);
        CloseConnection();
    }

    public static int deleteOffer(long id) {
        InitialConnection();
        int result = offerDao.delete(id);
        CloseConnection();
        return result;
    }

    public static Offer getOfferById(long id) {
        InitialConnection();
        Offer result = offerDao.getById(id);
        CloseConnection();
        return result;
    }

    public static List<Offer> getOfferList() {
        InitialConnection();
        List<Offer> result = offerDao.getAll();
        CloseConnection();
        return result;
    }
}