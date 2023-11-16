package com.example.server.Service;

import com.example.server.DBTransactions.DBManager;

public class SingletonIfClosed {
    static SingletonIfClosed instance;
    public static DBManager manager;
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

    public Boolean getIfClosed() {
        return ifClosed;
    }

    public void setIfClosed(Boolean ifClosed) {
        this.ifClosed = ifClosed;
    }
}
