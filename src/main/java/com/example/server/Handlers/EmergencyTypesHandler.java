package com.example.server.Handlers;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.TypesRep;
import com.example.server.Entities.TypeEmEntity;
import com.example.server.Handlers.Base.GetHandler;
import com.example.server.Service.SingletonIfClosed;
import com.google.gson.Gson;
import java.util.List;
import java.util.stream.Collectors;


public class EmergencyTypesHandler extends GetHandler {
    @Override
    protected String handleGet() {
        try {

            DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();
            List<TypeEmEntity> list = dbManager.getAllTypesEntities();
            Gson gson = new Gson();
            List<TypesRep> types = list.stream()
                    .map(i -> new TypesRep(i.getName(), i.getRecommendations())).collect(Collectors.toList());
            return gson.toJson(types);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
