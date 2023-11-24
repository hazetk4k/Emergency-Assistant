package com.example.server.Controllers.AdminPart;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.FullTypeRep;
import com.example.server.DBTransactions.SystemUsersRep;
import com.example.server.DBTransactions.TypeKindCharRep;
import com.example.server.Service.SingletonIfClosed;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();

    ObservableList<FullTypeRep> initialData() {
        return dbManager.getFullTypes();
    }

    public void initData() {
        type.setCellValueFactory(new PropertyValueFactory<FullTypeRep, String>("type"));
        recommendations.setCellValueFactory(new PropertyValueFactory<FullTypeRep, String>("recommendations"));
        kind.setCellValueFactory(new PropertyValueFactory<FullTypeRep, String>("kind"));
        tableTypes.setItems(initialData());
    }
}
