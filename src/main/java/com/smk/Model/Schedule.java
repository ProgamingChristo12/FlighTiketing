package com.smk.Model;

import java.util.Date;

public class Schedule  extends Model {
    private long id;
    private long depatureId;
    private Date depaturDate;
    private String flightNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDepatureId() {
        return depatureId;
    }

    public void setDepatureId(long depatureId) {
        this.depatureId = depatureId;
    }

    public Date getDepaturDate() {
        return depaturDate;
    }

    public void setDepaturDate(Date depaturDate) {
        this.depaturDate = depaturDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
