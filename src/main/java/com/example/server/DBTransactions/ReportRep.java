package com.example.server.DBTransactions;

import java.sql.Timestamp;

public class ReportRep {
    public String type;
    public String additionalInfo;
    public String place;
    public long timestamp;
    public Boolean areThereAnyCasualties;
    public String casualtiesAmount;
    public Boolean isUserInDanger;

    public ReportRep(String type, String additionalInfo, String place, long timestamp, Boolean areThereAnyCasualties, String casualtiesAmount, Boolean isUserInDanger) {
        this.type = type;
        this.additionalInfo = additionalInfo;
        this.place = place;
        this.timestamp = timestamp;
        this.areThereAnyCasualties = areThereAnyCasualties;
        this.casualtiesAmount = casualtiesAmount;
        this.isUserInDanger = isUserInDanger;
    }

    @Override
    public String toString() {
        return "ReportRep{" +
                "type='" + type + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", place='" + place + '\'' +
                ", timestamp=" + timestamp +
                ", areThereAnyCasualties=" + areThereAnyCasualties +
                ", casualtiesAmount=" + casualtiesAmount +
                ", isUserInDanger=" + isUserInDanger +
                '}';
    }
}
