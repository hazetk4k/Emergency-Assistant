package com.example.server.Controllers.AdminPart;

import com.example.server.DBTransactions.DBManager;
import com.example.server.Service.SingletonIfClosed;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.List;
import java.util.Objects;

public class RelationsViewController {
    public TreeView<String> relationsTreeView;
    public ChoiceBox<String> choiceBoxKinds;
    public ChoiceBox<String> choiceBoxServices;
    public TextField choosedKind;
    public TextField choosedService;
    public String kindOfService;
    public TextField textKindOfService;

    DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();

    public void initData() {
        initAdditional();
        List<String> kindNames = dbManager.getAllKindNames();

        choiceBoxKinds.getItems().addAll(dbManager.getAllKindNames());
        choiceBoxServices.getItems().addAll(dbManager.getAllServices());

        TreeItem<String> rootItem = new TreeItem<>("Виды чрезвычайных ситуаций");
        rootItem.setExpanded(true);

        for (String kindName : kindNames) {
            TreeItem<String> kindItem = new TreeItem<>(kindName);

            List<String> services = dbManager.getServicesByKind(kindName);

            for (String service : services) {
                TreeItem<String> serviceItem = new TreeItem<>(service);
                kindItem.getChildren().add(serviceItem);
            }
            rootItem.getChildren().add(kindItem);
        }

        relationsTreeView.setRoot(rootItem);

        relationsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                TreeItem<String> parentItem = newValue.getParent();
                if (parentItem != null) {
                    if (parentItem.getParent() == null) {
                        String selectedKind = newValue.getValue();
                        choosedKind.setText(selectedKind);
                    } else {
                        String selectedService = newValue.getValue();
                        kindOfService = parentItem.getValue();
                        choosedService.setText(selectedService);
                        textKindOfService.setText(kindOfService);
                    }
                }
            }
        });
    }

    private void initAdditional() {
        choiceBoxServices.getItems().clear();
        choiceBoxKinds.getItems().clear();

    }

    public void addNewRelation(ActionEvent actionEvent) {
        String kind = choiceBoxKinds.getSelectionModel().getSelectedItem();
        String service = choiceBoxServices.getSelectionModel().getSelectedItem();
        if (choiceBoxKinds.getSelectionModel().getSelectedItem() == null) {
            showAlert("Не выбран вид ЧС!", "Выберите вид ЧС, чтобы добавить связь!");
            return;
        }

        if (choiceBoxServices.getSelectionModel().getSelectedItem() == null) {
            showAlert("Не выбрана служба реагирования!", "Выберите службу реагирования на ЧС, чтобы добавить связь!");
            return;
        }

        int kind_id = dbManager.getKindIdByName(kind);
        int service_id = dbManager.getServiceIdByName(service);
        if (dbManager.ifThereSuchRelation(kind_id, service_id)) {
            showAlert("Связь не добавлена!", "Указанная связь уже существует!");
            return;
        }
        System.out.println("ID ВИДА: " + kind_id + ", ID СЛУЖБЫ: " + service_id);
        dbManager.addKindServiceRelation(kind_id, service_id);
        initData();
    }

    private void showAlert(String title, String warning) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(warning);
        alert.showAndWait();
    }

    public void deleteServiceRel(ActionEvent actionEvent) {
        if (choosedService.getText() == null || Objects.equals(choosedService.getText(), "")) {
            showAlert("Не выбрана служба!", "Выберите службу, связь с которой хотите убрать!");
            return;
        }

        int kind_id = dbManager.getKindIdByName(textKindOfService.getText());
        int service_id = dbManager.getServiceIdByName(choosedService.getText());
        dbManager.deleteThatRel(kind_id, service_id);
        initData();
    }

    public void deleteAllRels(ActionEvent actionEvent) {

        if (choosedKind.getText() == null || Objects.equals(choosedKind.getText(), "")) {
            showAlert("Не выбран вид ЧС!", "Выберите вид ЧС, чтобы удалить все ");
            return;
        }

        int kind_id = dbManager.getKindIdByName(choosedKind.getText());
        dbManager.deleteAllRels(kind_id);
        initData();
    }
}
