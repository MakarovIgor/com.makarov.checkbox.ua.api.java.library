package com.makarov.checkbox.ua.api;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.makarov.checkbox.ua.api.Exceptions.InvalidCredentialsException;
import com.makarov.checkbox.ua.api.Exceptions.NotActiveShiftException;
import com.makarov.checkbox.ua.api.Exceptions.ValidationException;
import com.makarov.checkbox.ua.api.Receipt.PngWidths;
import com.makarov.checkbox.ua.api.Receipt.Receipt;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.HashMap;
import java.util.Map;

public class CheckboxAPI {
    Config config;
    Map<String, String> headers;

    public CheckboxAPI(Config config) throws Exception {
        this.config = config;

        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("X-License-Key", config.get(Config.LICENSE_KEY));
    }

    public void cashierSignIn() throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(config.get(Config.API_URL) + "/api/v1/cashier/signin");
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("POST");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", config.get(Config.LOGIN));
        jsonObject.addProperty("password", config.get(Config.PASSWORD));

        RequestBody body = RequestBody.create(new Gson().toJson(jsonObject), MediaType.parse("application/json"));
        integrationRequest.setRequestBody(body);

        Response response = validateResponse(integrationRequest.request());

        JsonObject convertedObject = new Gson().fromJson(response.body().string(), JsonObject.class);

        setToken(convertedObject.get("access_token").getAsString());
    }

    public void cashierCreateShift() throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(config.get(Config.API_URL) + "/api/v1/shifts");
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("POST");

        Response response = validateResponse(integrationRequest.request());
    }

    public Receipt receiptsSell(Receipt receipt) throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(config.get(Config.API_URL) + "/api/v1/receipts/sell");
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("POST");

        String receiptBody = new Gson().toJson(receipt, Receipt.class);

        RequestBody body = RequestBody.create(receiptBody, MediaType.parse("application/json"));
        integrationRequest.setRequestBody(body);

        Response response = validateResponse(integrationRequest.request());

        receipt = new Gson().fromJson(response.body().string(), Receipt.class);

        return receipt;
    }

    public Receipt getReceipt(String receiptUUID) throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(config.get(Config.API_URL) + "/api/v1/receipts/" + receiptUUID);
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("GET");

        Response response = validateResponse(integrationRequest.request());

        Receipt receipt = new Gson().fromJson(response.body().string(), Receipt.class);

        return receipt;
    }

    public String getReceiptHtml(String receiptId, boolean isSimple) throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(
                config.get(Config.API_URL) + "/api/v1/receipts/" + receiptId + "/html?simple=" + isSimple
        );
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("GET");

        Response response = validateResponse(integrationRequest.request());
        return response.body().string();
    }

    public byte[] getReceiptPdf(String receiptId) throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(
                config.get(Config.API_URL) + "/api/v1/receipts/" + receiptId + "/pdf"
        );
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("GET");

        Response response = validateResponse(integrationRequest.request());

        return response.body().bytes();
    }

    public String getReceiptText(String receiptId, int width) throws Exception {
        if (width < 10) {
            width = 10;
        }

        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(
                config.get(Config.API_URL) + "/api/v1/receipts/" + receiptId + "/text?width=" + width
        );
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("GET");

        Response response = validateResponse(integrationRequest.request());
        return response.body().string();
    }

    public String getReceiptPngLink(String receiptId, PngWidths widths) throws Exception {
        String url = config.get(Config.API_URL) + "/api/v1/receipts/" + receiptId + "/png";
        if (widths != null) {
            url += "?" + widths.urlParameters();
        }
        return url;
    }

    public byte[] getReceiptPng(String receiptId, PngWidths widths) throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(getReceiptPngLink(receiptId, widths));
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("GET");

        Response response = validateResponse(integrationRequest.request());
        return response.body().bytes();
    }

    public byte[] getReceiptQrCode(String receiptId) throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(
                config.get(Config.API_URL) + "/api/v1/receipts/" + receiptId + "/qrcode"
        );
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("GET");

        Response response = validateResponse(integrationRequest.request());
        return response.body().bytes();
    }

    public void shiftsClose() throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(config.get(Config.API_URL) + "/api/v1/shifts/close");
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("POST");

        Response response = validateResponse(integrationRequest.request());
    }

    protected Response validateResponse(Response response) throws Exception {
        if (!response.isSuccessful()) {
            ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
            JsonObject body = new Gson().fromJson(responseBody.string(), JsonObject.class);
            String message = body.has("message") ? body.get("message").getAsString() : "";

            switch (response.code()) {
                case 400 -> throw new NotActiveShiftException(message);
                case 403 -> throw new InvalidCredentialsException(message);
                case 422 -> throw new ValidationException(message);
            }

            if (!message.isEmpty()) {
                throw new Exception(message);
            }
        }
        return response;
    }

    private void setToken(String token) {
        headers.put("Authorization", "Bearer " + token);
    }
}
