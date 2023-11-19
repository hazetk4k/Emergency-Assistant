package com.example.server.Controllers;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.ReportInfo;
import com.example.server.DBTransactions.ServicesRep;
import com.example.server.DBTransactions.TypeKindCharRep;
import com.example.server.Service.SingletonIfClosed;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ThatReportCont{
    @FXML
    public TextArea actionsTextArea;
    @FXML
    public TextArea recommendationsTextArea;
    @FXML
    public Button markResolvedButton;
    @FXML
    public Button generateReport;
    @FXML
    public ListView<ServicesRep> listOfServices;
    @FXML
    public TextArea EmergencyField;
    @FXML
    public TextField areThereAnyCasualties;
    @FXML
    public TextField amountOfCasualities;
    @FXML
    public TextField dateAndTime;
    @FXML
    public TextField isUserInDanger;
    @FXML
    public TextArea additionalData;
    @FXML
    public JFXButton endUpReaction;
    @FXML
    public JFXButton generateDoc;
    private ReportInfo rowData;

    public void initData(ReportInfo rowData) {
        this.rowData = rowData;
        System.out.println(rowData.getType());
        TypeKindCharRep info = getEmergencyData();


    }

    public TypeKindCharRep getEmergencyData(){
        DBManager dbManager =  SingletonIfClosed.getInstance().getDBManager();
        return dbManager.getTypeKindChar(rowData.getType());
    }

    public void markResolved(ActionEvent actionEvent) {

    }

    public void generateReport(ActionEvent actionEvent) {

    }
}
