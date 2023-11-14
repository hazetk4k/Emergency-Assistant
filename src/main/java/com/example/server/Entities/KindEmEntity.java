package com.example.server.Entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "kind_em", schema = "emergency", catalog = "")
public class KindEmEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "kind_id")
    private int kindId;
    @Basic
    @Column(name = "kind_name")
    private String kindName;
    @Basic
    @Column(name = "id_char")
    private Integer idChar;
    @ManyToOne
    @JoinColumn(name = "id_char", referencedColumnName = "char_id", insertable = false, updatable = false)
    private CharEmEntity charEmByIdChar;
    @OneToMany(mappedBy = "kindEmByKindId")
    private Collection<ServiceKindRelationEntity> serviceKindRelationsByKindId;
    @OneToMany(mappedBy = "kindEmByIdKind")
    private Collection<TypeEmEntity> typeEmsByKindId;

    public int getKindId() {
        return kindId;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public Integer getIdChar() {
        return idChar;
    }

    public void setIdChar(Integer idChar) {
        this.idChar = idChar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KindEmEntity that = (KindEmEntity) o;

        if (kindId != that.kindId) return false;
        if (kindName != null ? !kindName.equals(that.kindName) : that.kindName != null) return false;
        if (idChar != null ? !idChar.equals(that.idChar) : that.idChar != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kindId;
        result = 31 * result + (kindName != null ? kindName.hashCode() : 0);
        result = 31 * result + (idChar != null ? idChar.hashCode() : 0);
        return result;
    }

    public CharEmEntity getCharEmByIdChar() {
        return charEmByIdChar;
    }

    public void setCharEmByIdChar(CharEmEntity charEmByIdChar) {
        this.charEmByIdChar = charEmByIdChar;
    }

    public Collection<ServiceKindRelationEntity> getServiceKindRelationsByKindId() {
        return serviceKindRelationsByKindId;
    }

    public void setServiceKindRelationsByKindId(Collection<ServiceKindRelationEntity> serviceKindRelationsByKindId) {
        this.serviceKindRelationsByKindId = serviceKindRelationsByKindId;
    }

    public Collection<TypeEmEntity> getTypeEmsByKindId() {
        return typeEmsByKindId;
    }

    public void setTypeEmsByKindId(Collection<TypeEmEntity> typeEmsByKindId) {
        this.typeEmsByKindId = typeEmsByKindId;
    }
}
