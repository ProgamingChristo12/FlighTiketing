package com.smk.Model.dto;

import java.sql.Date;

public class ScheduleDTO {
    private  long id;
    private String depatureLocation;
    private String arrivalLocation;
    private Date depatureDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepatureLocation() {
        return depatureLocation;
    }

    public void setDepatureLocation(String depatureLocation) {
        this.depatureLocation = depatureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public Date getDepatureDate() {
        return depatureDate;
    }

    public void setDepatureDate(Date depatureDate) {
        this.depatureDate = depatureDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    private String flightNumber;
}
