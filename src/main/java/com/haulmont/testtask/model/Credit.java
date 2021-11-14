package com.haulmont.testtask.model;

public class Credit {
    private long id;
    private double limit;
    private double percent;

    public Credit(long id, double limit, double percent) {
        setId(id);
        setLimit(limit);
        setPercent(percent);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLimit() {
        return limit;
    }

    public double getPercent() {
        return percent;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
