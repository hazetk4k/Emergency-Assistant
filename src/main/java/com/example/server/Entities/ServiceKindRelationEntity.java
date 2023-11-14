package com.example.server.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "service_kind_relation", schema = "emergency", catalog = "")
public class ServiceKindRelationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_relation")
    private int idRelation;
    @Basic
    @Column(name = "service_id")
    private Integer serviceId;
    @Basic
    @Column(name = "kind_id")
    private Integer kindId;
    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", insertable = false, updatable = false)
    private ServiceEntity serviceByServiceId;
    @ManyToOne
    @JoinColumn(name = "kind_id", referencedColumnName = "kind_id", insertable = false, updatable = false)
    private KindEmEntity kindEmByKindId;

    public int getIdRelation() {
        return idRelation;
    }

    public void setIdRelation(int idRelation) {
        this.idRelation = idRelation;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceKindRelationEntity that = (ServiceKindRelationEntity) o;

        if (idRelation != that.idRelation) return false;
        if (serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null) return false;
        if (kindId != null ? !kindId.equals(that.kindId) : that.kindId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRelation;
        result = 31 * result + (serviceId != null ? serviceId.hashCode() : 0);
        result = 31 * result + (kindId != null ? kindId.hashCode() : 0);
        return result;
    }

    public ServiceEntity getServiceByServiceId() {
        return serviceByServiceId;
    }

    public void setServiceByServiceId(ServiceEntity serviceByServiceId) {
        this.serviceByServiceId = serviceByServiceId;
    }

    public KindEmEntity getKindEmByKindId() {
        return kindEmByKindId;
    }

    public void setKindEmByKindId(KindEmEntity kindEmByKindId) {
        this.kindEmByKindId = kindEmByKindId;
    }
}
