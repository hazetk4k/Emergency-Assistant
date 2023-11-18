package com.example.server.Controllers;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.ReportInfo;
import com.example.server.Service.SingletonIfClosed;
import javafx.animation.TranslateTransition;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class NewReportCont implements Initializable {
    @FXML
    public AnchorPane mainPain;
    @FXML
    private TableView<ReportInfo> tableView;
    @FXML
    public TableColumn<ReportInfo, String> type;
    @FXML
    public TableColumn<ReportInfo, Boolean> wasSeen;
    @FXML
    public TableColumn<ReportInfo, String> fio;
    @FXML
    public TableColumn<ReportInfo, String> place;
    @FXML
    public TableColumn<ReportInfo, Timestamp> timestamp;
    BaseCont cont = new BaseCont();
    public javafx.scene.control.Button button1;
    public javafx.scene.control.Button button2;
    public javafx.scene.control.Button button3;
    public Button toggleMenuButton;
    @FXML
    private VBox burgerMenu;

    ObservableList<ReportInfo> initialData() {
        DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();
        return dbManager.getUserReportsInfo();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.setCellValueFactory(new PropertyValueFactory<ReportInfo, String>("type"));
        timestamp.setCellValueFactory(new PropertyValueFactory<ReportInfo, Timestamp>("timestamp"));
        place.setCellValueFactory(new PropertyValueFactory<ReportInfo, String>("place"));
        fio.setCellValueFactory(new PropertyValueFactory<ReportInfo, String>("fio"));
        wasSeen.setCellValueFactory(new PropertyValueFactory<ReportInfo, Boolean>("wasSeen"));


        tableView.setItems(initialData());

        tableView.setRowFactory(tv -> {
            TableRow<ReportInfo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    ReportInfo rowData = row.getItem();
                    openNewWindow(rowData);
                }
            });
            return row;
        });
        wasSeen.setCellFactory(column -> new TableCell<ReportInfo, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item ? "Просмотрено" : "Новое");
                    setTextFill(item ? Color.GREEN : Color.RED);
                }
            }
        });
    }

    private void openNewWindow(ReportInfo rowData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("новое_окно.fxml"));
            Parent root = loader.load();

            ThatReportCont controller = loader.getController();
            controller.initData(rowData); // Передача параметра в контроллер нового окна

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toReportsList() throws IOException {
        cont.openFxmlScene("/all_reports.fxml");
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
}
