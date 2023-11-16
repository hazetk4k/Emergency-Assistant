package com.example.server.Handlers.Base;

import com.example.server.Handlers.Base.BaseHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;

public abstract class GetHandler extends BaseHandler {
    @Override
    protected void makeHandle(HttpExchange output) {
        try {
            String string = handleGet();
            byte[] bytes = string.getBytes();

            output.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = output.getResponseBody()) {
                os.write(bytes);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    protected abstract String handleGet();
}
