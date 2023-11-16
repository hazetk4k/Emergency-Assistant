package com.example.server.Handlers.Base;

import com.example.server.Handlers.Base.BaseHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class PostHandler extends BaseHandler {
    @Override
    protected void makeHandle(HttpExchange output) {
        try {
            InputStreamReader isr = new InputStreamReader(output.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            int code = handlePostInput(sb.toString());
            output.sendResponseHeaders(code, -1);
            br.close();
            isr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected abstract int handlePostInput(String output);
}
