package com.example.server.Controllers.AdminPart;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TypeViewController {
    @FXML
    public TableView tableTypes;
    @FXML
    public ChoiceBox kindChoiceBox;
    public Button btnDeleteType;
    @FXML
    public TextField choosedType;
    public Button btnAddNewTypr;
    @FXML
    public TextField fieldTypeName;

    public void initData() {
    }
}
