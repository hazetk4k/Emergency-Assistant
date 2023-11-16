package com.example.server.DBTransactions;

public class UserRep {
    public String name;
    public String surname;
    public String patronymic;
    public String homeAddress;
    public String workAddress;
    public String email;

    public UserRep(String name, String surname, String patronymic, String homeAddress, String workAddress, String email) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
        this.email = email;
    }
}
