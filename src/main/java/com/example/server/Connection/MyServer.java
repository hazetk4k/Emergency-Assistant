package com.example.server.Connection;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class MyServer extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(MyServer.class.getResource("/auth.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Сервер выключен");
                System.exit(0);
            }
        });
        stage.setTitle("Отслеживание новых заявлений");
        stage.show();
    }

    public static void main(String[] args) {
        ServerListener listener = new ServerListener();
        listener.start();
        launch();
    }
}


