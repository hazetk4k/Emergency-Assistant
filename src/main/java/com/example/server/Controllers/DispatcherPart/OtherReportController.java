package com.example.server.Controllers.DispatcherPart;

import com.example.server.DBTransactions.ChoiceRep;
import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.FullReportRep;
import com.example.server.DBTransactions.ReportInfo;
import com.example.server.Service.SingletonIfClosed;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;

public class OtherReportController {
    @FXML
    public TextField typeField; //сделано
    @FXML
    public ChoiceBox<String> chceBoxChar; //сделано
    @FXML
    public TextArea listOfServices; //рекомендация
    @FXML
    public ListView<String> listChsServices; //список выбора
    @FXML
    public TextField chsdServices; //выбор
    @FXML
    public Button btnCleanServices; //очистить выбор
    @FXML
    public TextField areThereAnyCasualties; //сделано
    @FXML
    public TextField amountOfCasualities; // сделано
    @FXML
    public TextField dateAndTime; //сделано
    @FXML
    public TextField isUserInDanger; //сделано
    @FXML
    public TextField fio; //сделано
    @FXML
    public TextField email; //сделано
    @FXML
    public TextField homeAddress; //сделано
    @FXML
    public TextArea additionalData; //сделано
    @FXML
    public Button resolveButton;
    @FXML
    public Button reportButton;
    @FXML
    public ComboBox<String> cmbBoxKinds; // сделано
    @FXML
    public TextArea recommendationsTextArea; //сделано
    @FXML
    public TextField place;
    DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();
    public ReportInfo rowData;

    public void initData(ReportInfo rowData) {
        // инициализация

        this.rowData = rowData;
        reportButton.setDisable(true);
        if (rowData.getWasSeen()) {
            resolveButton.setDisable(true);
            reportButton.setDisable(false);
        }
        // дополнительно
        typeField.setText(rowData.getType());
        List<String> charNames = dbManager.getAllCharNames();
        chceBoxChar.getItems().addAll(charNames);
        List<String> kindNames = dbManager.getAllKindNames();
        cmbBoxKinds.getItems().addAll(kindNames);

        if (dbManager.isThereChoice(rowData.getId()) > 0) {
            ChoiceRep choices = dbManager.getChoicesForReport(rowData.getId());
            chceBoxChar.setValue(choices.textChar);
            cmbBoxKinds.setValue(choices.textKind);
            chsdServices.setText(choices.services);
            chceBoxChar.setDisable(true);
            cmbBoxKinds.setDisable(true);
            chsdServices.setDisable(true);
            listChsServices.setDisable(true);
        }
        FullReportRep fullRep = dbManager.getFullReport(rowData.getId());


        // данные из fullRep
        if (fullRep.getUserInDanger()) {
            isUserInDanger.setText("Да");
        } else {
            isUserInDanger.setText("Нет");
        }
        if (fullRep.getAreThereAnyCasualties()) {
            areThereAnyCasualties.setText("Присутствуют");
            amountOfCasualities.setText(fullRep.getCasualtiesAmount());
        } else {
            areThereAnyCasualties.setText("Отсутствуют");
            amountOfCasualities.setDisable(true);
        }
        additionalData.setText(fullRep.getAdditionalInfo());
        homeAddress.setText(fullRep.getHome());
        email.setText(fullRep.getUserEmail());

        // данные из rowData
        fio.setText(rowData.getFio());
        place.setText(rowData.getPlace());
        dateAndTime.setText(rowData.getTimestamp().toString());
        recommendationsTextArea.setText("Cохраняйте спокойствие. Пострайтесь удалиться от\n" +
                "потенциальной опасности на безопасное расстояние\n" +
                "и ожидайте приезда служб.");

        List<String> servicesFromDB = dbManager.getAllServices();
        listChsServices.getItems().clear();
        listChsServices.getItems().addAll(servicesFromDB);
        listChsServices.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listChsServices.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);

        cmbBoxKinds.setOnAction(event -> {
            String selectedKind = cmbBoxKinds.getSelectionModel().getSelectedItem();
            if (selectedKind != null) {
                if (dbManager.isThereKind(selectedKind) != 0) {
                    List<String> servicesForKind = dbManager.getServicesByKind(selectedKind);
                    listOfServices.clear();
                    for (String service : servicesForKind) {
                        listOfServices.appendText(service + "\n");
                    }
                } else {
                    listOfServices.clear();
                }
            }
        });

    }


    private void selectionChanged(ObservableValue<? extends String> Observable, String oldVal, String newVal) {
        ObservableList<String> selectedItems = listChsServices.getSelectionModel().getSelectedItems();
        String getSelectedItem = (selectedItems.isEmpty()) ? "" : selectedItems.toString();
        chsdServices.setText(getSelectedItem);
    }

    public void generateReport(ActionEvent actionEvent) {

    }

    private boolean areFieldsFilled() {
        String choiceValue = chceBoxChar.getValue();
        String comboValue = cmbBoxKinds.getValue();
        String textValue = chsdServices.getText();

        return choiceValue != null && !choiceValue.isEmpty() &&
                comboValue != null && !comboValue.isEmpty() &&
                textValue != null && !textValue.isEmpty();
    }

    public void markResolved(ActionEvent actionEvent) {
        if (areFieldsFilled()) {
            resolveButton.setDisable(true);
            dbManager.resolveEmergency(rowData.getId());
            reportButton.setDisable(false);
            dbManager.makeDicision(chceBoxChar.getValue(), cmbBoxKinds.getValue(), chsdServices.getText(), rowData.getId());
            chsdServices.setDisable(true);
            cmbBoxKinds.setDisable(true);
            listChsServices.setDisable(true);
            showAlert();
        } else {
            showWarning();
        }

    }

    private void showWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Не заполнены поля!");
        alert.setHeaderText(null);
        LocalDateTime currentDateTime = LocalDateTime.now();
        alert.setContentText("Заполните характер, тип ЧС и выберите службы," +
                "\nкоторые нужно направить.");
        alert.showAndWait();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Выполнено");
        alert.setHeaderText(null);
        LocalDateTime currentDateTime = LocalDateTime.now();
        alert.setContentText("Ситуация разрешена.");
        alert.showAndWait();
    }

    public void cleanUpServices(ActionEvent actionEvent) {
        listChsServices.getSelectionModel().clearSelection();
    }
}
