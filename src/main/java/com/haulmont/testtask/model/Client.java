package com.haulmont.testtask.model;

public class Client {
    private long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String passNumber;

    public Client(long id, String name, String phoneNumber, String email, String passNumber) {
        setId(id);
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPassNumber(passNumber);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(String passNumber) {
        this.passNumber = passNumber;
    }
}
