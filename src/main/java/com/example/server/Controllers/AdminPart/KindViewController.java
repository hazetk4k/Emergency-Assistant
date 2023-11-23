package com.example.server.Controllers.AdminPart;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class KindViewController {
    @FXML
    public TableView tableRelation;
    public Button deleteKind;
    public Button addNewKind;
    @FXML
    public ChoiceBox charChoiceBox;
    public TextField newKindFiled;
    public Button addRelation;
    @FXML
    public ChoiceBox kindChoiceBox;
    @FXML
    public ChoiceBox serviceChoiceBox;
    @FXML
    public TableView tableKind;

    public void initData() {
    }
}
