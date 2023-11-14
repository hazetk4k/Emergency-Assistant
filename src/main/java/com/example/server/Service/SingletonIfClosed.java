package com.example.server.Service;

public class SingletonIfClosed {
    static SingletonIfClosed instance;
    Boolean ifClosed = true;
    public static SingletonIfClosed getInstance(){
        if (instance == null){
            instance = new SingletonIfClosed();
        }
        return instance;
    }

    public Boolean getIfClosed() {
        return ifClosed;
    }

    public void setIfClosed(Boolean ifClosed) {
        this.ifClosed = ifClosed;
    }
}
