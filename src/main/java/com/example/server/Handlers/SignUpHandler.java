package com.example.server.Handlers;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.UserObRep;
import com.example.server.DBTransactions.UserRep;
import com.example.server.Entities.UserDataEntity;
import com.example.server.Handlers.Base.PostHandler;
import com.example.server.Service.SingletonIfClosed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class SignUpHandler extends PostHandler {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public int handlePostInput(String output) {
        try {
            DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();
            UserObRep userOb = gson.fromJson(output, UserObRep.class);
            UserRep profile = userOb.profile;

            UserDataEntity userDataEntity = new UserDataEntity(profile.name,
                    profile.surname, profile.patronymic, profile.homeAddress,
                    profile.workAddress, profile.email, userOb.password);

            dbManager.addUserToDatabase(userDataEntity);
            System.out.println("Все прошло");
            return 200;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 500;
        }
    }
}






