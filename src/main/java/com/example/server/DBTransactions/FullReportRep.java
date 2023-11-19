package com.example.server.DBTransactions;

import jakarta.persistence.*;

import java.sql.Timestamp;

public class FullReportRep {
    private int idReport;
    private String type;
    private String additionalInfo;
    private String place;
    private Timestamp timestamp;
    private Boolean areThereAnyCasualties;
    private String casualtiesAmount;
    private Boolean isUserInDanger;
    private Boolean wasSeen;
    private String userEmail;
    private Timestamp recievedDateTime;
    private Timestamp endUpDateTime;
    private String home;

    public int getIdReport() {
        return idReport;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getAreThereAnyCasualties() {
        return areThereAnyCasualties;
    }

    public void setAreThereAnyCasualties(Boolean areThereAnyCasualties) {
        this.areThereAnyCasualties = areThereAnyCasualties;
    }

    public String getCasualtiesAmount() {
        return casualtiesAmount;
    }

    public void setCasualtiesAmount(String casualtiesAmount) {
        this.casualtiesAmount = casualtiesAmount;
    }

    public Boolean getUserInDanger() {
        return isUserInDanger;
    }

    public void setUserInDanger(Boolean userInDanger) {
        isUserInDanger = userInDanger;
    }

    public Boolean getWasSeen() {
        return wasSeen;
    }

    public void setWasSeen(Boolean wasSeen) {
        this.wasSeen = wasSeen;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Timestamp getRecievedDateTime() {
        return recievedDateTime;
    }

    public void setRecievedDateTime(Timestamp recievedDateTime) {
        this.recievedDateTime = recievedDateTime;
    }

    public Timestamp getEndUpDateTime() {
        return endUpDateTime;
    }

    public void setEndUpDateTime(Timestamp endUpDateTime) {
        this.endUpDateTime = endUpDateTime;
    }

    public FullReportRep(
            int idReport,
            String type,
            String additionalInfo,
            String place,
            Timestamp timestamp,
            Boolean areThereAnyCasualties,
            String casualtiesAmount,
            Boolean isUserInDanger,
            Boolean wasSeen,
            String userEmail,
            Timestamp recievedDateTime,
            Timestamp endUpDateTime,
            String home
    ) {
        this.idReport = idReport;
        this.type = type;
        this.additionalInfo = additionalInfo;
        this.place = place;
        this.timestamp = timestamp;
        this.areThereAnyCasualties = areThereAnyCasualties;
        this.casualtiesAmount = casualtiesAmount;
        this.isUserInDanger = isUserInDanger;
        this.wasSeen = wasSeen;
        this.userEmail = userEmail;
        this.recievedDateTime = recievedDateTime;
        this.endUpDateTime = endUpDateTime;
        this.home = home;
    }
}
