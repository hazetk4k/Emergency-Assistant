package com.example.server.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "disp_choice", schema = "emergency", catalog = "")
public class DispChoiceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name_char")
    private String nameChar;
    @Basic
    @Column(name = "name_type")
    private String nameType;
    @Basic
    @Column(name = "services")
    private String services;
    @OneToOne
    @JoinColumn(name = "repot_id", referencedColumnName = "id_report", nullable = false)
    private ReportsEntity reportsByRepotId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameChar() {
        return nameChar;
    }

    public void setNameChar(String nameChar) {
        this.nameChar = nameChar;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DispChoiceEntity that = (DispChoiceEntity) o;

        if (id != that.id) return false;
        if (nameChar != null ? !nameChar.equals(that.nameChar) : that.nameChar != null) return false;
        if (nameType != null ? !nameType.equals(that.nameType) : that.nameType != null) return false;
        if (services != null ? !services.equals(that.services) : that.services != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameChar != null ? nameChar.hashCode() : 0);
        result = 31 * result + (nameType != null ? nameType.hashCode() : 0);
        result = 31 * result + (services != null ? services.hashCode() : 0);
        return result;
    }

    public ReportsEntity getReportsByRepotId() {
        return reportsByRepotId;
    }

    public void setReportsByRepotId(ReportsEntity reportsByRepotId) {
        this.reportsByRepotId = reportsByRepotId;
    }
}
