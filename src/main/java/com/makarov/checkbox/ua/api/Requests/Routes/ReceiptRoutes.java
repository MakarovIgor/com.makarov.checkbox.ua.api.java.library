package com.makarov.checkbox.ua.api.Requests.Routes;

import com.makarov.checkbox.ua.api.Models.PngWidths;
import com.makarov.checkbox.ua.api.Requests.Methods;

public class ReceiptRoutes {
    String apiUrl;
    String route = "receipts";
    String fullRoute;

    public ReceiptRoutes(String apiUrlWithVersion) {
        this.apiUrl = apiUrlWithVersion;
        fullRoute = this.apiUrl + route + "/";
    }

    public Route sell() {
        return new Route(fullRoute + "sell", Methods.POST);
    }

    public Route getReceipt(String receiptId) {
        return new Route(fullRoute + receiptId);
    }

    public Route getReceiptPdf(String receiptId) {
        return new Route(fullRoute + receiptId + "/pdf");
    }

    public Route getReceiptQrCode(String receiptId) {
        return new Route(fullRoute + receiptId + "/qrcode");
    }

    public Route getReceiptHtml(String receiptId, boolean isSimple) {
        return new Route(fullRoute + receiptId + "/html?simple=" + isSimple);
    }

    public Route getReceiptText(String receiptId, int widthInCharacters) {
        int width = (widthInCharacters > 250) ? 250 : Math.max(widthInCharacters, 10);
        return new Route(fullRoute + receiptId + "/text?width=" + width);
    }

    public Route sendReceiptToEmail(String receiptId) {
        return new Route(fullRoute + receiptId + "/email", Methods.POST);
    }

    public Route service() {
        return new Route(fullRoute + "service", Methods.POST);
    }

    public Route getReceiptPng(String receiptId, PngWidths widths) {
        String url = fullRoute + receiptId + "/png";
        if (widths != null) {
            url += "?" + widths.urlParameters();
        }
        return new Route(url);
    }

}
