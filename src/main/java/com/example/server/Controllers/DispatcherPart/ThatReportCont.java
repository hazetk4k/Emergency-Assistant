package com.example.server.Controllers.DispatcherPart;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.FullReportRep;
import com.example.server.DBTransactions.ReportInfo;
import com.example.server.DBTransactions.TypeKindCharRep;
import com.example.server.Service.SingletonIfClosed;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;

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

    public void initData(ReportInfo rowData) {
        this.rowData = rowData;
        reportButton.setDisable(true);
        if (rowData.getWasSeen()) {
            resolveButton.setDisable(true);
            reportButton.setDisable(false);
        }

        TypeKindCharRep info = getEmergencyData();
        FullReportRep fullRep = dbManager.getFullReport(rowData.getId());

        if (info != null) {
            ObservableList<String> servicesList = FXCollections.observableArrayList();
            servicesList.addAll(info.getServices());
            listOfServices.setItems(servicesList);

            EmergencyField.setText("Указанный заявителем Тип ЧС:\t\t\t" + info.getName() + ";\n" + "Определен вид ЧС:\t\t\t\t\t" + info.getKind_name() + ";\n" + "Характер ЧС:\t\t\t\t\t\t\t" + info.getChar_name() + ";\n\n" + "Направить службы по указанному адресу: " + rowData.getPlace() + ".");
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
        reportButton.setDisable(false);
        showAlert();
    }

    public void generateReport(ActionEvent actionEvent) {

    }
}
