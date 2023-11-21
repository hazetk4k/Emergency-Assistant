package com.example.server.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "syst_user", schema = "emergency", catalog = "")
public class SystUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_syst")
    private int idSyst;
    @Basic
    @Column(name = "login_syst")
    private String loginSyst;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "status_syst")
    private byte statusSyst;

    public int getIdSyst() {
        return idSyst;
    }

    public void setIdSyst(int idSyst) {
        this.idSyst = idSyst;
    }

    public String getLoginSyst() {
        return loginSyst;
    }

    public void setLoginSyst(String loginSyst) {
        this.loginSyst = loginSyst;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getStatusSyst() {
        return statusSyst;
    }

    public void setStatusSyst(byte statusSyst) {
        this.statusSyst = statusSyst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystUserEntity that = (SystUserEntity) o;

        if (idSyst != that.idSyst) return false;
        if (statusSyst != that.statusSyst) return false;
        if (loginSyst != null ? !loginSyst.equals(that.loginSyst) : that.loginSyst != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSyst;
        result = 31 * result + (loginSyst != null ? loginSyst.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (int) statusSyst;
        return result;
    }
}
