package com.example.server.DBTransactions;

import java.sql.Timestamp;

public class ReportInfo {
    private String type;
    private Timestamp timestamp;
    private String place;
    private String name;
    private String surname;
    private String patronymic;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Boolean getWasSeen() {
        return wasSeen;
    }

    public void setWasSeen(Boolean wasSeen) {
        this.wasSeen = wasSeen;
    }

    public ReportInfo(String type, Timestamp timestamp, String place, String name, String surname, String patronymic, Boolean wasSeen) {
        this.type = type;
        this.timestamp = timestamp;
        this.place = place;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.wasSeen = wasSeen;
    }
}