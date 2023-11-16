package com.example.server.Controllers;

import com.example.server.Service.SingletonIfClosed;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainCont implements Initializable {
    public javafx.scene.control.Button Button;
    BaseCont cont = new BaseCont();


    public void OnButton() throws IOException {
        cont.openFxmlScene("/new_report_menu.fxml");
        SingletonIfClosed.getInstance().setIfClosed(true);
        Stage stage = (Stage) Button.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}