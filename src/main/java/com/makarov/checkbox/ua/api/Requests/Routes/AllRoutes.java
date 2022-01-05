package com.makarov.checkbox.ua.api.Requests.Routes;

import com.makarov.checkbox.ua.api.Models.PngWidths;

public class AllRoutes {
    String apiUrl;
    String apiVersion = "v1";

    public AllRoutes(String apiUrl) {
        this.apiUrl = apiUrl + "/api/" + apiVersion + "/";
    }

    public Route cashierSignIn() {
        return new Cashier(this.apiUrl).signIn();
    }

    public Route cashierSignOut() {
        return new Cashier(this.apiUrl).signOut();
    }

    public Route cashierProfile() {
        return new Cashier(this.apiUrl).getProfile();
    }

    public Route openShift() {
        return new Shift(this.apiUrl).open();
    }

    public Route closeShift() {
        return new Shift(this.apiUrl).close();
    }

    public Route sellReceipt() {
        return new Receipt(apiUrl).sell();
    }

    public Route getReceipt(String receiptId) {
        return new Receipt(apiUrl).getReceipt(receiptId);
    }

    public Route getReceiptPdf(String receiptId) {
        return new Receipt(apiUrl).getReceiptPdf(receiptId);
    }

    public Route getReceiptQrCode(String receiptId) {
        return new Receipt(apiUrl).getReceiptQrCode(receiptId);
    }

    public Route getReceiptHtml(String receiptId, boolean isSimple) {
        return new Receipt(apiUrl).getReceiptHtml(receiptId, isSimple);
    }

    public Route getReceiptText(String receiptId, int width) {
        return new Receipt(apiUrl).getReceiptText(receiptId, width);
    }

    public Route getReceiptPng(String receiptId, PngWidths widths) {
        return new Receipt(apiUrl).getReceiptPng(receiptId, widths);
    }

    public Route sendReceiptToEmail(String receiptId) {
        return new Receipt(apiUrl).sendReceiptToEmail(receiptId);
    }

    public Route createServiceReceipt() {
        return new Receipt(apiUrl).service();
    }
    public Route getAllTaxes() {
        return new Route(apiUrl + "tax");
    }

    public Route pingTaxService() {
        return new CashRegisters(apiUrl).pingTaxService();
    }
}
