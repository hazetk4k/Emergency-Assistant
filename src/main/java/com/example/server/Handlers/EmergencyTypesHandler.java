package com.example.server.Handlers;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.TypesRep;
import com.example.server.Entities.TypeEmEntity;
import com.example.server.Service.SingletonIfClosed;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;


public class EmergencyTypesHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {

            DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();
            List<TypeEmEntity> list = dbManager.getAllTypesEntities();
            Gson gson = new Gson();
            List<TypesRep> types = list.stream()
                    .map(i -> new TypesRep(i.getName(), i.getRecommendations())).collect(Collectors.toList());
            String string = gson.toJson(types);
            byte[] bytes = string.getBytes();
            System.out.println("types");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }

        } catch (Exception e) {

            exchange.sendResponseHeaders(500, e.getMessage().length());
            String errorMessage = "Internal Server Error: " + e.getMessage();
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(errorMessage.getBytes());
            }
            throw new RuntimeException(e);
        }
    }
}
