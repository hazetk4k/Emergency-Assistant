package com.example.server.Entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "service", schema = "emergency", catalog = "")
public class ServiceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "service_id")
    private int serviceId;
    @Basic
    @Column(name = "service_name")
    private String serviceName;
    @OneToMany(mappedBy = "serviceByServiceId")
    private Collection<ServiceKindRelationEntity> serviceKindRelationsByServiceId;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceEntity that = (ServiceEntity) o;

        if (serviceId != that.serviceId) return false;
        if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceId;
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        return result;
    }

    public Collection<ServiceKindRelationEntity> getServiceKindRelationsByServiceId() {
        return serviceKindRelationsByServiceId;
    }

    public void setServiceKindRelationsByServiceId(Collection<ServiceKindRelationEntity> serviceKindRelationsByServiceId) {
        this.serviceKindRelationsByServiceId = serviceKindRelationsByServiceId;
    }
}
