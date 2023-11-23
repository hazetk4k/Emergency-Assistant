package com.example.server.Controllers.AdminPart;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.ReportInfo;
import com.example.server.DBTransactions.SystemUsersRep;
import com.example.server.Service.SingletonIfClosed;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class UserViewController {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    public RadioButton radIsAdmin;
    public Button btnAddUser;
    public Button btnDelete;
    @FXML
    public ChoiceBox<String> choiceBoxStatus;
    public Button btnChangeStatus;
    @FXML
    public TableView<SystemUsersRep> systUserTable;
    public TableColumn<SystemUsersRep, Integer> id_syst;
    public TableColumn<SystemUsersRep, String> login_syst;
    public TableColumn<SystemUsersRep, String> status_syst;
    public TextField choosedProfile;
    DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();
    public int userId;

    ObservableList<SystemUsersRep> initialData() {
        return dbManager.getSystUsers();
    }

    public void initData() {
        choiceBoxStatus.getItems().clear();
        choiceBoxStatus.getItems().addAll("Администратор", "Диспетчер");
        id_syst.setCellValueFactory(new PropertyValueFactory<SystemUsersRep, Integer>("id_syst"));
        login_syst.setCellValueFactory(new PropertyValueFactory<SystemUsersRep, String>("login_syst"));
        status_syst.setCellValueFactory(new PropertyValueFactory<SystemUsersRep, String>("status_syst"));
        systUserTable.setItems(initialData());

        systUserTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String value = newSelection.getLogin_syst();
                userId = newSelection.getId_syst();
                choosedProfile.setText(value);
            }
        });
    }

    public void addNewUser(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        byte status;
        if (radIsAdmin.isSelected()) {
            status = 1;
        } else {
            status = 0;
        }
        dbManager.addNewUser(login, password, status);
        initData();
    }

    public void deleteUser(ActionEvent actionEvent) {
        if (choosedProfile.getText() != null && !Objects.equals(choosedProfile.getText(), "")) {
            if (Objects.equals(SingletonIfClosed.getInstance().getCurrentUser(), choosedProfile.getText())) {
                showAlert("Попытка удаления активного профиля!", "Остановлена попытка собственного профиля!");
            } else {
                if (systUserTable.getSelectionModel().isEmpty()) {
                    showAlert("Не выбран элемент таблицы!", "Выберите поле таблицы, которое хотите удалить.");
                } else {
                    dbManager.deleteSystUser(userId);
                    initData();
                }
            }
        } else {
            showAlert("Пустой выбор!", "Выберите поле из таблицы, которые хотите удалить!");
        }
    }

    private void showAlert(String title, String warning) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(warning);
        alert.showAndWait();
    }

    public void changeUserStatus(ActionEvent actionEvent) {
        if (choiceBoxStatus.getSelectionModel().getSelectedItem() == null) {
            showAlert("Не выбран статус!", "Выберите статус, который будет применен к профилю!");
        } else {
            if (systUserTable.getSelectionModel().isEmpty()) {
                showAlert("Не выбран элемент таблицы!", "Выберите поле, в котором хотите изменить статус");
            } else {
                byte n = 0;
                if (Objects.equals(choiceBoxStatus.getSelectionModel().getSelectedItem(), "Администратор")) {
                    n = 1;
                }
                dbManager.changeUserStatus(userId, n);
                initData();
            }
        }
    }
}
