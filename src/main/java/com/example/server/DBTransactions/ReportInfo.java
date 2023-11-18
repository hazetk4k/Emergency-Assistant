package com.example.server.DBTransactions;

import java.sql.Timestamp;

public class ReportInfo {
    private String type;
    private Timestamp timestamp;
    private String place;
    private String fio;
    private Boolean wasSeen;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Boolean getWasSeen() {
        return wasSeen;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setWasSeen(Boolean wasSeen) {
        this.wasSeen = wasSeen;
    }

    public ReportInfo(String type, Timestamp timestamp, String place, String fio, Boolean wasSeen) {
        this.type = type;
        this.timestamp = timestamp;
        this.place = place;
        this.fio = fio;
        this.wasSeen = wasSeen;
    }
}