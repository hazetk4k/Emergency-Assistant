package com.example.server.Handlers;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.UserRep;
import com.example.server.Entities.UserDataEntity;
import com.example.server.Service.SingletonIfClosed;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ProfileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();
            String query = exchange.getRequestURI().getQuery();
            String email = null;

            if (query != null) {
                String[] queryParams = query.split("=");
                if (queryParams.length == 2 && "email".equals(queryParams[0])) {
                    email = queryParams[1];
                }
            }
            Gson gson = new Gson();
            if (email != null) {
                UserDataEntity userProfile = dbManager.getUserByEmailAndPassword(email);
                UserRep user = new UserRep(userProfile.getName(),
                        userProfile.getSurname(),
                        userProfile.getPatronymic(),
                        userProfile.getHomeAddress(),
                        userProfile.getWorkAddress(),
                        userProfile.getEmail());
                String string = gson.toJson(user);
                byte[] bytes = string.getBytes();

                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } else {
                exchange.sendResponseHeaders(500, -1);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            exchange.sendResponseHeaders(500, -1);
        }
    }
}

