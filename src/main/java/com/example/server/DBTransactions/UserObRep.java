package com.example.server.DBTransactions;

public class UserObRep {

    public UserRep profile;
    public String password;

    public UserObRep(UserRep profile, String password) {
        this.profile = profile;
        this.password = password;
    }
}
