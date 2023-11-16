package com.example.server.Handlers;

import com.example.server.DBTransactions.DBManager;
import com.example.server.DBTransactions.ReportObRep;
import com.example.server.DBTransactions.ReportRep;
import com.example.server.Entities.ReportsEntity;
import com.example.server.Handlers.Base.PostHandler;
import com.example.server.Service.SingletonIfClosed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;

public class ReportHandler extends PostHandler {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected int handlePostInput(String output) {
        try {
            DBManager dbManager = SingletonIfClosed.getInstance().getDBManager();

            ReportObRep reportObRep = gson.fromJson(output, ReportObRep.class);
            System.out.println(reportObRep.toString());

            ReportRep rep1 = reportObRep.report;
            ReportsEntity report = new ReportsEntity();

            report.setAdditionalInfo(rep1.additionalInfo);
            report.setType(rep1.type);
            report.setPlace(rep1.place);
            report.setTimestamp(new Timestamp(rep1.timestamp));
            report.setAdditionalInfo(rep1.additionalInfo);
            report.setCasualtiesAmount(rep1.casualtiesAmount);
            report.setIsUserInDanger(rep1.isUserInDanger);
            report.setAreThereAnyCasualties(rep1.areThereAnyCasualties);
            report.setUserEmail(reportObRep.email);
            report.setWasSeen(false);

            dbManager.addReportToDatabase(report);
            System.out.println("Все прошло");
            return 200;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 500;
        }
    }
}
