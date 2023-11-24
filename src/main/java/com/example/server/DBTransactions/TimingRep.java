package com.example.server.DBTransactions;

import java.sql.Timestamp;

public class TimingRep {

    public Timestamp recieved_date_time;
    public Timestamp end_up_datetime;

    public Timestamp getRecieved_date_time() {
        return recieved_date_time;
    }

    public void setRecieved_date_time(Timestamp recieved_date_time) {
        this.recieved_date_time = recieved_date_time;
    }

    public Timestamp getEnd_up_datetime() {
        return end_up_datetime;
    }

    public void setEnd_up_datetime(Timestamp end_up_datetime) {
        this.end_up_datetime = end_up_datetime;
    }

    public TimingRep(Timestamp recieved_date_time, Timestamp end_up_datetime) {
        this.recieved_date_time = recieved_date_time;
        this.end_up_datetime = end_up_datetime;
    }
}
