package com.example.server.Entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "reports", schema = "emergency", catalog = "")
public class ReportsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_report")
    private int idReport;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "additional_info")
    private String additionalInfo;
    @Basic
    @Column(name = "place")
    private String place;
    @Basic
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Basic
    @Column(name = "areThereAnyCasualties")
    private Boolean areThereAnyCasualties;
    @Basic
    @Column(name = "casualtiesAmount")
    private String casualtiesAmount;
    @Basic
    @Column(name = "isUserInDanger")
    private Boolean isUserInDanger;
    @Basic
    @Column(name = "wasSeen")
    private Boolean wasSeen;
    @Basic
    @Column(name = "user_email")
    private String userEmail;
    @Basic
    @Column(name = "recieved_date_time")
    private Timestamp recievedDateTime;
    @Basic
    @Column(name = "end_up_datatime")
    private Timestamp endUpDateTime;
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", insertable = false, updatable = false)
    private UserDataEntity userByEmail;
    @OneToOne(mappedBy = "reportsByRepotId")
    private DispChoiceEntity dispChoicesByIdReport;

    public int getIdReport() {
        return idReport;
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

    public Boolean getIsUserInDanger() {
        return isUserInDanger;
    }

    public void setIsUserInDanger(Boolean isUserInDanger) {
        this.isUserInDanger = isUserInDanger;
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

    public UserDataEntity getUserByEmail() {
        return userByEmail;
    }

    public void setUserByEmail(UserDataEntity userByEmail) {
        this.userByEmail = userByEmail;
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

    public Boolean getUserInDanger() {
        return isUserInDanger;
    }

    public void setUserInDanger(Boolean userInDanger) {
        isUserInDanger = userInDanger;
    }

    public DispChoiceEntity getDispChoicesByIdReport() {
        return dispChoicesByIdReport;
    }

    public void setDispChoicesByIdReport(DispChoiceEntity dispChoicesByIdReport) {
        this.dispChoicesByIdReport = dispChoicesByIdReport;
    }
}
