package com.example.server.Controllers;

import com.example.server.Service.SingletonIfClosed;
import javafx.animation.TranslateTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewReportCont implements Initializable {
    BaseCont cont = new BaseCont();
    public javafx.scene.control.Button button1;
    public javafx.scene.control.Button button2;
    public javafx.scene.control.Button button3;
    public Button toggleMenuButton;
    @FXML
    private VBox burgerMenu;

    @FXML
    private void toggleMenu() {
        TranslateTransition menuAnimation = new TranslateTransition(Duration.millis(300), burgerMenu);

        if (!burgerMenu.isVisible()) {
            menuAnimation.setToY(0);
            burgerMenu.setVisible(true);
            toggleMenuButton.setText("✖");
            menuAnimation.play();
        } else {
            menuAnimation.setToY(-burgerMenu.getWidth());
            burgerMenu.setVisible(false);
            toggleMenuButton.setText("☰");
            menuAnimation.play();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button1.setStyle("-fx-font-size: 14px;");
        button2.setStyle("-fx-font-size: 14px;");
        button3.setStyle("-fx-font-size: 14px;");

    }

    public void toReportsList() throws IOException {
        cont.openFxmlScene("/new.fxml");
        SingletonIfClosed.getInstance().setIfClosed(true);
        Stage stage = (Stage) button1.getScene().getWindow();
        stage.close();
    }

    public void openSaveDirectory(ActionEvent actionEvent) {
    }


    public void toAuthWindow() throws IOException {
        cont.openFxmlScene("/auth.fxml");
        SingletonIfClosed.getInstance().setIfClosed(true);
        Stage stage = (Stage) button3.getScene().getWindow();
        stage.close();
    }
}
