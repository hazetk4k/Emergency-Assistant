package com.example.server.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "type_em", schema = "emergency", catalog = "")
public class TypeEmEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "recommendations")
    private String recommendations;
    @Basic
    @Column(name = "id_kind")
    private Integer idKind;
    @ManyToOne
    @JoinColumn(name = "id_kind", referencedColumnName = "kind_id", insertable = false, updatable = false)
    private KindEmEntity kindEmByIdKind;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public Integer getIdKind() {
        return idKind;
    }

    public void setIdKind(Integer idKind) {
        this.idKind = idKind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeEmEntity that = (TypeEmEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (recommendations != null ? !recommendations.equals(that.recommendations) : that.recommendations != null)
            return false;
        if (idKind != null ? !idKind.equals(that.idKind) : that.idKind != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (recommendations != null ? recommendations.hashCode() : 0);
        result = 31 * result + (idKind != null ? idKind.hashCode() : 0);
        return result;
    }

    public KindEmEntity getKindEmByIdKind() {
        return kindEmByIdKind;
    }

    public void setKindEmByIdKind(KindEmEntity kindEmByIdKind) {
        this.kindEmByIdKind = kindEmByIdKind;
    }
}
