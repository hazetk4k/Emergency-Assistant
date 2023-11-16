package com.example.server.DBTransactions;

public class ReportObRep {
    public String email;
    public ReportRep report;

    public ReportObRep(String email, ReportRep report) {
        this.email = email;
        this.report = report;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ReportObRep{" +
                "email='" + email + '\'' +
                ", reportRep=" + report +
                '}';
    }
}
