package com.haulmont.testtask.model;

public class Bank {
    private long id;
    private long bankId;
    private Client client;
    private Credit credit;

    public Bank(long id, long bankId, Client client, Credit credit) {
        setId(id);
        setBankId(bankId);
        setClient(client);
        setCredit(credit);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
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

    public String getClientName() {
        return (client == null) ? "" : client.getName();
    }

    public String getCreditDescription() {
        return (credit == null) ? "" : "Лимит: " + credit.getLimit() + " Процент: " + credit.getPercent();
    }
}
