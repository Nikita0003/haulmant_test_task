package com.haulmont.testtask.model;

import java.time.LocalDate;

public class Offer {
    private long id;
    private Client  client;
    private Credit credit;
    private double totalSum;
    private LocalDate dateOffer;
    private int countPayment;
    private LocalDate nextDate;
    private double nextSum;
    private double bodySum;
    private double percentSum;

    public Offer(long id, Client client, Credit credit, double totalSum,
                 LocalDate dateOffer, int countPayment,
                 LocalDate nextDate, double nextSum, double bodySum, double percentSum) {
        this.id = id;
        this.client = client;
        this.credit = credit;
        this.totalSum = totalSum;
        this.dateOffer = dateOffer;
        this.countPayment = countPayment;
        this.nextDate = nextDate;
        this.nextSum = nextSum;
        this.bodySum = bodySum;
        this.percentSum = percentSum;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
            this.totalSum = totalSum;
    }

    public LocalDate getDateOffer() {
        return dateOffer;
    }

    public void setDateOffer(LocalDate dateOffer) {
        this.dateOffer = dateOffer;
    }

    public int getCountPayment() {
        return countPayment;
    }

    public void setCountPayment(int countPayment) {
        this.countPayment = countPayment;
    }

    public void makingPayment() {
        countPayment--;
        nextDate = nextDate.plusMonths(1);
    }

    public String getClientName()
    {
        return client.getName();
    }

    public String getCreditDescription()
    {
        return "Лимит: " + credit.getLimit() + " Процент: " + credit.getPercent();
    }


    public LocalDate getNextDate() {
        return nextDate;
    }

    public void setNextDate(LocalDate nextDate) {
        this.nextDate = nextDate;
    }

    public double getNextSum() {
        return nextSum;
    }

    public void setNextSum(double nextSum) {
        this.nextSum = nextSum;
    }

    public double getBodySum() {
        return bodySum;
    }

    public void setBodySum(double bodySum) {
        this.bodySum = bodySum;
    }

    public double getPercentSum() {
        return percentSum;
    }

    public void setPercentSum(double percentSum) {
        this.percentSum = percentSum;
    }
}
