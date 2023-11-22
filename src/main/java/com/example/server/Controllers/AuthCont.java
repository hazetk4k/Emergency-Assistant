package com.example.server.Controllers;

import com.example.server.DBTransactions.DBManager;
import com.example.server.Service.SingletonIfClosed;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthCont implements Initializable {
    public javafx.scene.control.Button Button;
    @FXML
    public TextField fieldLogin;
    @FXML
    public PasswordField fieldPassword;
    public Label labelProgress;
    BaseCont cont = new BaseCont();


    public void OnButton() throws IOException {
        DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();
        String login = fieldLogin.getText();
        String password = fieldPassword.getText();
        String answer = dbManager.checkAuthParametrs(login, password);
        if (answer == null) {
            fieldLogin.clear();
            fieldLogin.setStyle("-fx-prompt-text-fill: red");
            fieldLogin.setPromptText("Пользователь не найден");
            fieldPassword.clear();
        } else if (answer.contains("incorrect pas")) {
            fieldPassword.clear();
            fieldPassword.setStyle("-fx-prompt-text-fill: red");
            fieldPassword.setPromptText("Неверный пароль");
        } else if (answer.contains("админ")) {
            cont.openFxmlScene("/admin_menu.fxml", "Управление информацией по чс");
            SingletonIfClosed.getInstance().setIfClosed(true);
            Stage stage = (Stage) Button.getScene().getWindow();
            stage.close();
        } else if (answer.contains("диспетчер")) {
            cont.openFxmlScene("/new_report_menu.fxml", "Отслеживание новых заявлений");
            SingletonIfClosed.getInstance().setIfClosed(true);
            Stage stage = (Stage) Button.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
