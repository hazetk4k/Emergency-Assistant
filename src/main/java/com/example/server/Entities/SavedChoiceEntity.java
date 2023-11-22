package com.example.server.Entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "saved_choice", schema = "emergency", catalog = "")
public class SavedChoiceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_saved")
    private int idSaved;
    @Basic
    @Column(name = "char")
    private String char_name;
    @Basic
    @Column(name = "kind")
    private String kind_name;
    @Basic
    @Column(name = "services")
    private String services;
    @OneToMany(mappedBy = "savedChoiceByExclusiveSaved")
    private Collection<ReportsEntity> reportsByIdSaved;

    public int getIdSaved() {
        return idSaved;
    }

    public void setIdSaved(int idSaved) {
        this.idSaved = idSaved;
    }

    public String getChar_name() {
        return char_name;
    }

    public void setChar_name(String char_name) {
        this.char_name = char_name;
    }

    public String getKind_name() {
        return kind_name;
    }

    public void setKind_name(String kind_name) {
        this.kind_name = kind_name;
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

        SavedChoiceEntity that = (SavedChoiceEntity) o;

        if (idSaved != that.idSaved) return false;
        if (char_name != null ? !char_name.equals(that.char_name) : that.char_name != null) return false;
        if (kind_name != null ? !kind_name.equals(that.kind_name) : that.kind_name != null) return false;
        if (services != null ? !services.equals(that.services) : that.services != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSaved;
        result = 31 * result + (char_name != null ? char_name.hashCode() : 0);
        result = 31 * result + (kind_name != null ? kind_name.hashCode() : 0);
        result = 31 * result + (services != null ? services.hashCode() : 0);
        return result;
    }

    public Collection<ReportsEntity> getReportsByIdSaved() {
        return reportsByIdSaved;
    }

    public void setReportsByIdSaved(Collection<ReportsEntity> reportsByIdSaved) {
        this.reportsByIdSaved = reportsByIdSaved;
    }
}
