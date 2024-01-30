package com.example.server.Controllers.DispatcherPart;

import com.example.server.DBTransactions.*;
import com.example.server.Service.DateTimeFormatExample;
import com.example.server.Service.DocxToPdfConverter;
import com.example.server.Service.SingletonIfClosed;
import com.example.server.Service.WordReportGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ThatReportCont {
    @FXML
    public TextArea recommendationsTextArea;//сделано
    @FXML
    public ListView<String> listOfServices; //сделано
    @FXML
    public TextArea EmergencyField; //сделано
    @FXML
    public TextField areThereAnyCasualties; //сделано
    @FXML
    public TextField amountOfCasualities; //сделано
    @FXML
    public TextField dateAndTime; //сделано
    @FXML
    public TextField isUserInDanger; // переделать
    @FXML
    public TextArea additionalData; //сделано
    @FXML
    public TextField fio; //сделано
    @FXML

    public TextField email; //сделано
    @FXML
    public TextField homeAddress; //сделано
    public Button resolveButton; //сделано
    public Button reportButton;
    private ReportInfo rowData;
    DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();
    private FullReportRep thatReport;

    public String this_kind;
    public String this_char;
    public String this_services;

    DateTimeFormatExample format = new DateTimeFormatExample();

    public void initData(ReportInfo rowData) {
        this.rowData = rowData;
        reportButton.setDisable(true);
        if (rowData.getWasSeen()) {
            resolveButton.setDisable(true);
            reportButton.setDisable(false);
        }
        TypeKindCharRep info = getEmergencyData();
        FullReportRep fullRep = dbManager.getFullReport(rowData.getId());
        ObservableList<String> servicesList = FXCollections.observableArrayList();

        if (dbManager.isThereChoice(rowData.getId()) > 0) {
            ChoiceRep choices = dbManager.getChoicesForReport(rowData.getId());
            this_kind = choices.textKind;
            this_char = choices.textChar;
            this_services = choices.services;
            EmergencyField.setText("Указанный заявителем Тип ЧС:\t\t\t" + info.getName() +
                    ";\n" + "Определен вид ЧС:\t\t\t\t\t" + choices.textKind + ";\n" +
                    "Характер ЧС:\t\t\t\t\t\t\t" + choices.textChar + ";\n\n" +
                    "Направить службы по указанному адресу: " + rowData.getPlace() +
                    ".");
            String[] elements = choices.services.split(",\\s*");
            servicesList.addAll(elements);
            listOfServices.setItems(servicesList);
        } else {
            servicesList.addAll(info.getServices());
            listOfServices.setItems(servicesList);
            this_kind = info.getKind_name();
            this_char = info.getChar_name();
            this_services = String.join(", ", servicesList);
            EmergencyField.setText("Указанный заявителем Тип ЧС:\t\t\t" + info.getName() +
                    ";\n" + "Определен вид ЧС:\t\t\t\t\t" + info.getKind_name() + ";\n" +
                    "Характер ЧС:\t\t\t\t\t\t\t" + info.getChar_name() + ";\n\n" +
                    "Направить службы по указанному адресу: " + rowData.getPlace() +
                    ".");
        }

        dateAndTime.setText(rowData.getTimestamp().toString());
        fio.setText(rowData.getFio());

        if (fullRep.getAreThereAnyCasualties()) {
            areThereAnyCasualties.setText("Присутствуют");
            amountOfCasualities.setText(fullRep.getCasualtiesAmount());
        } else {
            areThereAnyCasualties.setText("Отсутствуют");
            amountOfCasualities.setDisable(true);
        }
        if (fullRep.getUserInDanger()) {
            isUserInDanger.setText("Да");
        } else {
            isUserInDanger.setText("Нет");
        }

        additionalData.setText(fullRep.getAdditionalInfo());
        homeAddress.setText(fullRep.getHome());
        email.setText(fullRep.getUserEmail());
        recommendationsTextArea.setText(info.getRecommendation());
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Выполнено");
        alert.setHeaderText(null);
        LocalDateTime currentDateTime = LocalDateTime.now();
        alert.setContentText("Ситуация разрешена.");
        alert.showAndWait();
    }

    public TypeKindCharRep getEmergencyData() {
        return dbManager.getTypeKindChar(rowData.getType());
    }

    public void markResolved(ActionEvent actionEvent) {
        resolveButton.setDisable(true);
        dbManager.resolveEmergency(rowData.getId());
        dbManager.makeDicision(this_char, this_kind, this_services, rowData.getId());
        reportButton.setDisable(false);
        showAlert();
    }

    public void generateReport(ActionEvent actionEvent) {
        WordReportGenerator reportGenerator = new WordReportGenerator();
        Map<String, String> reportData = gatherReportData();
        reportGenerator.generateReport(reportData);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Выполнено");
        alert.setHeaderText(null);
        alert.setContentText("Создан отчет по заявлению №" + rowData.getId());
        alert.showAndWait();
    }

    private Map<String, String> gatherReportData() {

        TimingRep timings = dbManager.getTimings(rowData.getId());

        Map<String, String> data = new HashMap<>();
        data.put("if[]", "no");
        data.put("Num", String.valueOf(rowData.getId()));
        data.put("Type", rowData.getType());
        data.put("Char", this_char);
        data.put("Kind", this_kind);
        data.put("place", rowData.getPlace() );
        data.put("services", this_services);

        data.put("recieved_date_time", format.dateTimeChange(timings.getRecieved_date_time().toString()));
        data.put("fio", fio.getText());
        data.put("email", email.getText());
        data.put("address", homeAddress.getText());
        data.put("recommendations", recommendationsTextArea.getText());
        data.put("areThereCasualities", areThereAnyCasualties.getText());
        data.put("amountOfCasualities", amountOfCasualities.getText());
        data.put("isUserInDanger", isUserInDanger.getText());
        data.put("additionalData", additionalData.getText());
        data.put("логин", SingletonIfClosed.getInstance().getCurrentUser());
        data.put("end_up_datetime", format.dateTimeChange(timings.getEnd_up_datetime().toString()));
        data.put("timestamp", dateAndTime.getText());
        return data;

    }
}
