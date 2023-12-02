package com.example.server.Controllers.AdminPart;

import com.example.server.Controllers.BaseCont;
import com.example.server.Service.SingletonIfClosed;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenu implements Initializable {
    public VBox burgerMenu;
    public Button btnOpenKinds;
    public Button btnOpenTypes;
    public Button btnOpenUsers;
    public Button btnBackToAuth;
    public Button btnOpenRels;
    BaseCont cont = new BaseCont();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void backToAuth() throws IOException {
        cont.openFxmlScene("/auth.fxml", "Авторизация");
        SingletonIfClosed.getInstance().setIfClosed(true);
        Stage stage = (Stage) btnBackToAuth.getScene().getWindow();
        stage.close();
        String resource = "/auth.fxml";
    }

    public void openUserOptions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user_table_view.fxml"));
            Parent root = loader.load();

            UserViewController controller = loader.getController();
            controller.initData();

            Stage stage = new Stage();
            stage.setTitle("Управление пользователями");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void openTypeOptions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/type_view_manu.fxml"));
            Parent root = loader.load();

            TypeViewController controller = loader.getController();
            controller.initData();

            Stage stage = new Stage();
            stage.setTitle("Управление типами ЧС");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void openKindOptions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kind_view_menu.fxml"));
            Parent root = loader.load();

            KindViewController controller = loader.getController();
            controller.initData();

            Stage stage = new Stage();
            stage.setTitle("Управление видами ЧС");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void openRelationsOptions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/relations_view_menu.fxml"));
            Parent root = loader.load();

            RelationsViewController controller = loader.getController();
            controller.initData();

            Stage stage = new Stage();
            stage.setTitle("Управление связями между видом ЧС и службами реагирования");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
