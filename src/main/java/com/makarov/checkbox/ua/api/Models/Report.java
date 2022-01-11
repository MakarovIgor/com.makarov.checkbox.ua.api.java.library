package com.makarov.checkbox.ua.api.Models;

import com.makarov.checkbox.ua.api.Models.Payment.ReportPayment;
import com.makarov.checkbox.ua.api.Models.Tax.ReportTax;

import java.util.ArrayList;

public class Report {
    String id;
    int serial;
    boolean is_z_report;
    ArrayList<ReportPayment> payments;
    ArrayList<ReportTax> taxes;
    int sell_receipts_count;
    int return_receipts_count;
    int initial;
    int balance;

    public Report(
            String id,
            int serial,
            boolean isZReport,
            ArrayList<ReportPayment> payments,
            ArrayList<ReportTax> taxes,
            int sellReceiptsCount,
            int returnReceiptsCount,
            int initialBalance,
            int balance
    ) {
        this.id = id;
        this.serial = serial;
        this.is_z_report = isZReport;
        this.payments = payments;
        this.taxes = taxes;
        this.sell_receipts_count = sellReceiptsCount;
        this.return_receipts_count = returnReceiptsCount;
        this.initial = initialBalance;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }
}
