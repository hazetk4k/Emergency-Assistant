package com.example.server.Handlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SignInHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }
            br.close();
            isr.close();



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
