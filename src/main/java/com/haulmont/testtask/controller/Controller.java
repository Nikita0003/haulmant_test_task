package com.haulmont.testtask.controller;

import com.haulmont.testtask.dao.*;
import com.haulmont.testtask.model.*;

import java.util.List;


public class Controller {
    private static DBManager dbManager = new DBManager();
    private static ClientDao clientDao = new ClientDao(dbManager);
    private static CreditDao creditDao = new CreditDao(dbManager);
    private static BankDao bankDao = new BankDao(dbManager);
    private static OfferDao offerDao = new OfferDao(dbManager);

    public static void addClient(Client client)
    {
        clientDao.add(client);
    }

    public static void updateClient(Client client)
    {
        clientDao.update(client);
    }

    public static int deleteClient(long id)
    {
        return clientDao.delete(id);
    }

    public static Client getClientById(long id)
    {
        return clientDao.getById(id);
    }

    public static List<Client> getClientList()
    {
        return clientDao.getAll();
    }

    public static void addCredit(Credit credit)
    {
        creditDao.add(credit);
    }

    public static void updateCredit(Credit credit)
    {
        creditDao.update(credit);
    }

    public static int deleteCredit(long id)
    {
        return creditDao.delete(id);
    }

    public static Credit getCreditById(long id)
    {
        return creditDao.getById(id);
    }

    public static List<Credit> getCreditList()
    {
        return creditDao.getAll();
    }

    public static void addBank(Bank bank)
    {
        bankDao.add(bank);
    }

    public static void updateBank(Bank bank)
    {
        bankDao.update(bank);
    }

    public static int deleteBank(long id)
    {
        return bankDao.delete(id);
    }

    public static Bank getBankById(long id)
    {
        return bankDao.getById(id);
    }

    public static List<Bank> getBankList()
    {
        return bankDao.getAll();
    }

    public static void addOffer(Offer offer)
    {
        offerDao.add(offer);
    }

    public static void updateOffer(Offer offer)
    {
        offerDao.update(offer);
    }

    public static int deleteOffer(long id)
    {
        return offerDao.delete(id);
    }

    public static Offer getOfferById(long id)
    {
        return offerDao.getById(id);
    }

    public static List<Offer> getOfferList()
    {
        return offerDao.getAll();
    }
}