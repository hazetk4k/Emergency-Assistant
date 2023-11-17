package com.example.server.Handlers;

import com.example.server.DBTransactions.AuthRep;
import com.example.server.DBTransactions.DBManager;

import com.example.server.Entities.UserDataEntity;
import com.example.server.Handlers.Base.PostHandler;
import com.example.server.Service.SingletonIfClosed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;


public class SignInHandler extends PostHandler {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public int handlePostInput(String output) {
        try {
            DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();
            AuthRep authRep = gson.fromJson(output, AuthRep.class);
            UserDataEntity userDataEntity = dbManager.getUserByEmailAndPassword(authRep.email);
            if (userDataEntity != null) {
                if (Objects.equals(userDataEntity.getPassword(), authRep.password)) {
                    System.out.println("Все прошло");
                    return 200;
                } else {
                    System.out.println("Не верный пароль");
                    return 401;
                }
            } else {
                System.out.println("Пользователь не найден");
                return 404;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 500;
        }
    }
}
