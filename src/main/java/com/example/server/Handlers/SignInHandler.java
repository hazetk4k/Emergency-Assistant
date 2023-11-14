package com.example.server.Handlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SignInHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {

            exchange.sendResponseHeaders(200, 0);
        }catch (Exception e){
            exchange.sendResponseHeaders(500, 0);
            String errorMessage = "Internal Server Error: " + e.getMessage();
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(errorMessage.getBytes());
            }
        }
    }
}
