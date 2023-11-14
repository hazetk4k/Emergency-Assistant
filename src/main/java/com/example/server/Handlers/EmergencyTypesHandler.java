package com.example.server.Handlers;

import com.example.server.DBTransactions.DBManager;
import com.example.server.Entities.TypeEmEntity;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class EmergencyTypesHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {

            DBManager manager = new DBManager();
            List<TypeEmEntity> list = manager.getAllTypesEntities();
            System.out.println(list.get(0).getName());
            exchange.sendResponseHeaders(200, 0);
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, 0);
            String errorMessage = "Internal Server Error: " + e.getMessage();
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(errorMessage.getBytes());
            }
            throw new RuntimeException(e);
        }
    }
}
