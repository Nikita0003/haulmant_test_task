package com.haulmont.testtask.dao;

import com.haulmont.testtask.controller.DBManager;

import java.util.List;

public interface Dao<T> {
    void setDBManager(DBManager manager);
    void add(T entity);
    void update(T entity);
    int delete(long id);
    List<T> getAll();
    T getById(long id);
}
