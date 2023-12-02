package com.example.server.Controllers.AdminPart;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.FulliKindRep;
import com.example.server.Service.SingletonIfClosed;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Objects;

public class KindViewController {
    public Button deleteKind;
    public Button addNewKind;
    @FXML
    public ChoiceBox<String> charChoiceBox;
    public TextField newKindFiled;
    @FXML
    public TableView<FulliKindRep> tableKind;
    public TableColumn<FulliKindRep, Integer> id_kind;
    public TableColumn<FulliKindRep, String> kind_name;
    public TableColumn<FulliKindRep, String> char_name;
    public TextField choosedKind;
    DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();

    ObservableList<FulliKindRep> initialTableData() {
        return dbManager.getFullKinds();
    }

    public void initAdditonal() {
        charChoiceBox.getItems().clear();
    }

    private void showAlert(String title, String warning) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(warning);
        alert.showAndWait();
    }

    public void initData() {
        initAdditonal();
        charChoiceBox.getItems().addAll(dbManager.getAllCharNames());
        id_kind.setCellValueFactory(new PropertyValueFactory<FulliKindRep, Integer>("kind_id"));
        kind_name.setCellValueFactory(new PropertyValueFactory<FulliKindRep, String>("kind_name"));
        char_name.setCellValueFactory(new PropertyValueFactory<FulliKindRep, String>("char_name"));
        tableKind.setItems(initialTableData());
        tableKind.setOnMouseClicked(event -> {
            FulliKindRep selectedItem = tableKind.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String selectedKind = selectedItem.getKind_name();
                choosedKind.setText(selectedKind);
            }
        });
    }

    public void deleteThisKind(ActionEvent actionEvent) {
        if (choosedKind.getText() == null || Objects.equals(choosedKind.getText(), "")) {
            showAlert("Не выбрана запись!", "Выберите запись для удаления!");
        } else {
            if(dbManager.ifThereTypesOfKind(choosedKind.getText())){
                showAlert("Запись не удалена!", "К данному виду относятся некоторые типы!");
                return;
            }
            dbManager.deleteThisKind(choosedKind.getText());
            initData();
        }
    }

    public void addNewKind(ActionEvent actionEvent) {
        if (charChoiceBox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Не выбран характер ЧС!", "Выберите характер ЧС, к которому будет относиться вид!");
            return;
        }

        String newKindText = newKindFiled.getText();
        if (newKindText == null || newKindText.isEmpty()) {
            showAlert("Не введено название!", "Введите название вида!");
            return;
        }

        if (dbManager.isThereKind(newKindText) != 0) {
            showAlert("Запись не добавлена!", "Запись с данным названием уже существует!");
            return;
        }

        int id = dbManager.getCharIdByName(charChoiceBox.getSelectionModel().getSelectedItem());
        dbManager.addNewKind(newKindText, id);
        initData();
    }

}
