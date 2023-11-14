package com.example.server.Entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "char_em", schema = "emergency", catalog = "")
public class CharEmEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "char_id")
    private int charId;
    @Basic
    @Column(name = "char_name")
    private String charName;
    @OneToMany(mappedBy = "charEmByIdChar")
    private Collection<KindEmEntity> kindEmsByCharId;

    public int getCharId() {
        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharEmEntity that = (CharEmEntity) o;

        if (charId != that.charId) return false;
        if (charName != null ? !charName.equals(that.charName) : that.charName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = charId;
        result = 31 * result + (charName != null ? charName.hashCode() : 0);
        return result;
    }

    public Collection<KindEmEntity> getKindEmsByCharId() {
        return kindEmsByCharId;
    }

    public void setKindEmsByCharId(Collection<KindEmEntity> kindEmsByCharId) {
        this.kindEmsByCharId = kindEmsByCharId;
    }
}
