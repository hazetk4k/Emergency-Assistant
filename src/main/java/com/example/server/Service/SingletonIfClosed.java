package com.example.server.Service;

import com.example.server.Controllers.DispatcherPart.NewReportCont;
import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.ReportInfo;
import javafx.collections.ObservableList;

public class SingletonIfClosed {
    static SingletonIfClosed instance;
    public static DBManager manager;
    private NewReportCont controller;

    Boolean ifClosed = true;
    public static SingletonIfClosed getInstance(){
        if (instance == null){
            instance = new SingletonIfClosed();
        }
        return instance;
    }

    public DBManager getDBManager() {
        if (manager == null) {
            manager = new DBManager();
        }
        return manager;
    }

    public void setController(NewReportCont controller) {
        this.controller = controller;
    }

    public void updateTableWithData(ObservableList<ReportInfo> newData) {
        if (controller != null) {
            controller.updateTableWithData(newData);
        }
    }

    public Boolean getIfClosed() {
        return ifClosed;
    }

    public void setIfClosed(Boolean ifClosed) {
        this.ifClosed = ifClosed;
    }
}
