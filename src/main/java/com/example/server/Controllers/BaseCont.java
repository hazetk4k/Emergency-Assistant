package com.example.server.Controllers;

import com.example.server.Connection.MyServer;
import com.example.server.Service.SingletonIfClosed;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;

import java.io.IOException;

public class BaseCont {

    public void openFxmlScene(String name, String naming) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(naming);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (SingletonIfClosed.getInstance().getIfClosed()) {
                    System.out.println("Сервер выключен");
                    System.exit(0);
                }
                SingletonIfClosed.getInstance().setIfClosed(true);
            }

        });
        FXMLLoader fxmlLoader = new FXMLLoader(MyServer.class.getResource(name));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

}
