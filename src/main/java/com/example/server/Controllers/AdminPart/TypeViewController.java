package com.example.server.Controllers.AdminPart;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.FullTypeRep;
import com.example.server.DBTransactions.SystemUsersRep;
import com.example.server.DBTransactions.TypeKindCharRep;
import com.example.server.Service.SingletonIfClosed;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Objects;

public class TypeViewController {
    @FXML
    public TableView<FullTypeRep> tableTypes;
    @FXML
    public ChoiceBox<String> kindChoiceBox;
    public Button btnDeleteType;
    @FXML
    public TextField choosedType;
    public Button btnAddNewTypr;
    @FXML
    public TextField fieldTypeName;
    public TableColumn<FullTypeRep, String> type;
    public TableColumn<FullTypeRep, String> recommendations;
    public TableColumn<FullTypeRep, String> kind;
    public TextArea recoms;
    DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();

    ObservableList<FullTypeRep> initialData() {
        return dbManager.getFullTypes();
    }

    public void initData() {
        kindChoiceBox.getItems().clear();
        kindChoiceBox.getItems().addAll(dbManager.getAllKindNames());
        type.setCellValueFactory(new PropertyValueFactory<FullTypeRep, String>("type"));
        recommendations.setCellValueFactory(new PropertyValueFactory<FullTypeRep, String>("recommendations"));
        kind.setCellValueFactory(new PropertyValueFactory<FullTypeRep, String>("kind"));
        tableTypes.setItems(initialData());
        tableTypes.setOnMouseClicked(event -> {
            FullTypeRep selectedItem = tableTypes.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String selectedType = selectedItem.getType();
                choosedType.setText(selectedType);
            }
        });
    }

    public void deleteType(ActionEvent actionEvent) {
        if(choosedType.getText() == null || Objects.equals(choosedType.getText(), "")){
            showAlert("Не выбрана запись!","Выберите запись для удаления!");
        }else{
            dbManager.deleteThisType(choosedType.getText());
            initData();
        }
    }

    private void showAlert(String title, String warning) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(warning);
        alert.showAndWait();
    }

    public void addNewType(ActionEvent actionEvent) {
        if (kindChoiceBox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Не выбран вид ЧС!", "Выберите вид, к которому будет относится тип!");
        } else {
            if (fieldTypeName.getText() == null || Objects.equals(fieldTypeName.getText(), "")) {
                showAlert("Не введено название!", "Введите название типа");
            } else {
                if (recoms.getText() == null || Objects.equals(recoms.getText(), "")) {
                    showAlert("Не дана рекомендация!", "Введите рекомендацию для пользователя!");
                } else {
                    if(dbManager.isTypeNameExists(fieldTypeName.getText())){
                        showAlert("Запись не добавлена!", "Запись с данным названием уже существует!");
                    }else{
                        int id = dbManager.getKindIdByName(kindChoiceBox.getSelectionModel().getSelectedItem());
                        dbManager.addNewType(fieldTypeName.getText(), recoms.getText(), id);
                        initData();
                    }
                }
            }
        }
    }
}
