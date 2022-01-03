package com.makarov.checkbox.ua.api;

//import okhttp3.FormBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.makarov.checkbox.ua.api.Receipt.PngWidths;
import com.makarov.checkbox.ua.api.Receipt.Receipt;
import netscape.javascript.JSObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
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

        String response = integrationRequest.send();
        JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);

        setToken(convertedObject.get("access_token").getAsString());

        System.out.println("cashierSignIn " + response);
    }

    public void cashierCreateShift() throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(config.get(Config.API_URL) + "/api/v1/shifts");
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("POST");

        String response = integrationRequest.send();

        System.out.println("cashierCreateShift " + response);
    }

    public Receipt receiptsSell(Receipt receipt) throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(config.get(Config.API_URL) + "/api/v1/receipts/sell");
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("POST");

        String receiptBody = new Gson().toJson(receipt, Receipt.class);

        RequestBody body = RequestBody.create(receiptBody, MediaType.parse("application/json"));
        System.out.println("receiptBody " + receiptBody);
        integrationRequest.setRequestBody(body);

        String response = integrationRequest.send();
        receipt = new Gson().fromJson(response, Receipt.class);
        System.out.println("receiptsSell " + response);

        return receipt;
    }

    public Receipt getReceipt(String receiptUUID) throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(config.get(Config.API_URL) + "/api/v1/receipts/" + receiptUUID);
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("GET");

        String response = integrationRequest.send();
        Receipt receipt = new Gson().fromJson(response, Receipt.class);
        System.out.println("getReceipt " + response);

        return receipt;
    }

    public String getReceiptPng(String receiptUUID, PngWidths pngWidths) throws Exception {
       /* headers.put("Content-Type", "image/png");

        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(config.get(Config.API_URL) + "/api/v1/receipts/" + receiptUUID + "/png");
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("GET");

        String response = integrationRequest.send();
       // Receipt receipt = new Gson().fromJson(response, Receipt.class);
        System.out.println("getReceiptPng " + response);
        headers.put("Content-Type", "application/json");*/
       /* URL url = new URL(imageURL);
        InputStream is = url.openStream();
        byte[] bytes = is.readAllBytes();
        return Base64.encodeBase64String(bytes);*/

        return config.get(Config.API_URL) + "/api/v1/receipts/" + receiptUUID + "/png";
    }

    public void shiftsClose() throws Exception {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setUrl(config.get(Config.API_URL) + "/api/v1/shifts/close");
        integrationRequest.setHeaders(headers);
        integrationRequest.setMethod("POST");
        String response = integrationRequest.send();
        System.out.println("shiftsClose " + response);
    }


    private void setToken(String token) {
        headers.put("Authorization", "Bearer " + token);
    }
}
