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
    private Byte areThereAnyCasualties;
    @Basic
    @Column(name = "casualtiesAmount")
    private Integer casualtiesAmount;
    @Basic
    @Column(name = "isUserInDanger")
    private Byte isUserInDanger;
    @Basic
    @Column(name = "wasSeen")
    private Byte wasSeen;
    @Basic
    @Column(name = "user_email")
    private String userEmail;
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", insertable = false, updatable = false)
    private UserDataEntity userDataByUserEmail;

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

    public Byte getAreThereAnyCasualties() {
        return areThereAnyCasualties;
    }

    public void setAreThereAnyCasualties(Byte areThereAnyCasualties) {
        this.areThereAnyCasualties = areThereAnyCasualties;
    }

    public Integer getCasualtiesAmount() {
        return casualtiesAmount;
    }

    public void setCasualtiesAmount(Integer casualtiesAmount) {
        this.casualtiesAmount = casualtiesAmount;
    }

    public Byte getIsUserInDanger() {
        return isUserInDanger;
    }

    public void setIsUserInDanger(Byte isUserInDanger) {
        this.isUserInDanger = isUserInDanger;
    }

    public Byte getWasSeen() {
        return wasSeen;
    }

    public void setWasSeen(Byte wasSeen) {
        this.wasSeen = wasSeen;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportsEntity that = (ReportsEntity) o;

        if (idReport != that.idReport) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (additionalInfo != null ? !additionalInfo.equals(that.additionalInfo) : that.additionalInfo != null)
            return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (areThereAnyCasualties != null ? !areThereAnyCasualties.equals(that.areThereAnyCasualties) : that.areThereAnyCasualties != null)
            return false;
        if (casualtiesAmount != null ? !casualtiesAmount.equals(that.casualtiesAmount) : that.casualtiesAmount != null)
            return false;
        if (isUserInDanger != null ? !isUserInDanger.equals(that.isUserInDanger) : that.isUserInDanger != null)
            return false;
        if (wasSeen != null ? !wasSeen.equals(that.wasSeen) : that.wasSeen != null) return false;
        if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idReport;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (areThereAnyCasualties != null ? areThereAnyCasualties.hashCode() : 0);
        result = 31 * result + (casualtiesAmount != null ? casualtiesAmount.hashCode() : 0);
        result = 31 * result + (isUserInDanger != null ? isUserInDanger.hashCode() : 0);
        result = 31 * result + (wasSeen != null ? wasSeen.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        return result;
    }

    public UserDataEntity getUserDataByUserEmail() {
        return userDataByUserEmail;
    }

    public void setUserDataByUserEmail(UserDataEntity userDataByUserEmail) {
        this.userDataByUserEmail = userDataByUserEmail;
    }
}
