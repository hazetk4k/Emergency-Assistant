package com.example.server.Connection;

import com.example.server.Handlers.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class ServerListener extends Thread{
    @Override
    public void run() {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);

            // Регистрация обработчиков для разных контекстов
            server.createContext("/auth/signup", new SignUpHandler());
            server.createContext("/auth/signin", new SignInHandler());
            server.createContext("/profile", new ProfileHandler());
            server.createContext("/emergencytypes", new EmergencyTypesHandler());
            server.createContext("/sendreport", new ReportHandler());

            server.setExecutor(null);
            server.start();
            System.out.println("Server is listening on port 8080");

            // Ожидание ввода пользователя для завершения работы сервера
            System.out.println("Press 'q' and Enter to stop the server.");
            Scanner scanner = new Scanner(System.in);
            while (!scanner.nextLine().equalsIgnoreCase("q")) {

            }

        } catch (IOException e) {
            e.printStackTrace(); // Обработка ошибок исключений
        } finally {
            if (server != null) {
                server.stop(0);
                System.out.println("Server stopped");
            }
        }
    }
}
