package com.example.server.Controllers;

import com.example.server.DBTransactions.ReportInfo;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ThatReportCont implements Initializable {
    public TextArea actionsTextArea;
    public TextArea recommendationsTextArea;
    public Button markResolvedButton;
    public Button generateReport;
    private ReportInfo rowData;

    public void initData(ReportInfo rowData) {
        this.rowData = rowData;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void markResolved(ActionEvent actionEvent) {
    }

    public void generateReport(ActionEvent actionEvent) {
    }
}
