package com.makarov.checkbox.ua.api.Requests.Routes;

import com.makarov.checkbox.ua.api.Models.PngWidths;

public class AllRoutes {
    String apiUrl;

    public AllRoutes(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Route cashierSignIn() {
        return new CashierRoutes(apiUrl).signIn();
    }

    public Route cashierSignOut() {
        return new CashierRoutes(apiUrl).signOut();
    }

    public Route cashierProfile() {
        return new CashierRoutes(apiUrl).getProfile();
    }

    public Route getCashierActiveShift() {
        return new CashierRoutes(apiUrl).getCashierShift();
    }
    public Route openShift() {
        return new ShiftRoutes(apiUrl).open();
    }

    public Route closeShift() {
        return new ShiftRoutes(apiUrl).close();
    }

    public Route sellReceipt() {
        return new ReceiptRoutes(apiUrl).sell();
    }

    public Route getReceipt(String receiptId) {
        return new ReceiptRoutes(apiUrl).getReceipt(receiptId);
    }

    public Route getReceiptPdf(String receiptId) {
        return new ReceiptRoutes(apiUrl).getReceiptPdf(receiptId);
    }

    public Route getReceiptQrCode(String receiptId) {
        return new ReceiptRoutes(apiUrl).getReceiptQrCode(receiptId);
    }

    public Route getReceiptHtml(String receiptId, boolean isSimple) {
        return new ReceiptRoutes(apiUrl).getReceiptHtml(receiptId, isSimple);
    }

    public Route getReceiptText(String receiptId, int width) {
        return new ReceiptRoutes(apiUrl).getReceiptText(receiptId, width);
    }

    public Route getReceiptPng(String receiptId, PngWidths widths) {
        return new ReceiptRoutes(apiUrl).getReceiptPng(receiptId, widths);
    }

    public Route sendReceiptToEmail(String receiptId) {
        return new ReceiptRoutes(apiUrl).sendReceiptToEmail(receiptId);
    }

    public Route createServiceReceipt() {
        return new ReceiptRoutes(apiUrl).service();
    }

    public Route getAllTaxes() {
        return new Route(apiUrl + "tax");
    }

    public Route pingTaxService() {
        return new CashRegistersRoutes(apiUrl).pingTaxService();
    }

    public Route createXReport() {
        return new ReportRoutes(apiUrl).createXReport();
    }

    public Route getReport(String reportId) {
        return new ReportRoutes(apiUrl).getReport(reportId);
    }

    public Route getReportText(String reportId) {
        return new ReportRoutes(apiUrl).getReportText(reportId);
    }

    public Route getReportText(String reportId, int width) {
        return new ReportRoutes(apiUrl).getReportText(reportId, width);
    }
    public Route getShift(String shiftId) {
        return new ShiftRoutes(apiUrl).getShift(shiftId);
    }

    public Route getShifts() {
        return new ShiftRoutes(apiUrl).getShifts();
    }
}
