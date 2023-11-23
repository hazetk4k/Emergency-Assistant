package com.example.server.DBTransactions;

public class ChoiceRep {
    public String textChar;
    public String textKind;
    public String services;

    public String getTextChar() {
        return textChar;
    }

    public void setTextChar(String textChar) {
        this.textChar = textChar;
    }

    public String getTextKind() {
        return textKind;
    }

    public void setTextKind(String textKind) {
        this.textKind = textKind;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public ChoiceRep(String textChar, String textKind, String services) {
        this.textChar = textChar;
        this.textKind = textKind;
        this.services = services;
    }
}
