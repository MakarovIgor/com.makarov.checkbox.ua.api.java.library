package com.makarov.checkbox.ua.api;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.makarov.checkbox.ua.api.Exceptions.InvalidCredentialsException;
import com.makarov.checkbox.ua.api.Exceptions.NotActiveShiftException;
import com.makarov.checkbox.ua.api.Exceptions.ValidationException;
import com.makarov.checkbox.ua.api.Receipt.PngWidths;
import com.makarov.checkbox.ua.api.Receipt.Receipt;
import com.makarov.checkbox.ua.api.Receipt.Tax;
import com.makarov.checkbox.ua.api.Requests.RequestSender;
import com.makarov.checkbox.ua.api.Requests.Routes.AllRoutes;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckboxAPI {
    Config config;
    Map<String, String> headers;
    AllRoutes routes;

    public CheckboxAPI(Config config) throws Exception {
        this.config = config;
        this.routes = new AllRoutes(config.get(Config.API_URL));

        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("X-License-Key", config.get(Config.LICENSE_KEY));
    }

    public void cashierSignIn() throws Exception {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", config.get(Config.LOGIN));
        jsonObject.addProperty("password", config.get(Config.PASSWORD));
        RequestBody body = RequestBody.create(new Gson().toJson(jsonObject), MediaType.parse("application/json"));

        RequestSender integrationRequest = new RequestSender(routes.cashierSignIn());
        integrationRequest.setHeaders(headers);
        integrationRequest.setRequestBody(body);

        Response response = validateResponse(integrationRequest.request());

        JsonObject convertedObject = new Gson().fromJson(response.body().string(), JsonObject.class);

        setToken(convertedObject.get("access_token").getAsString());
    }

    public void cashierSignOut() throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.cashierSignOut());
        integrationRequest.setHeaders(headers);

        validateResponse(integrationRequest.request());
    }

    //TODO return Cashier
    public void getCashierProfile() throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.cashierProfile());
        integrationRequest.setHeaders(headers);

        validateResponse(integrationRequest.request());
    }

    public void openShift() throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.openShift());
        integrationRequest.setHeaders(headers);

        Response response = validateResponse(integrationRequest.request());
    }

    public void closeShift() throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.closeShift());
        integrationRequest.setHeaders(headers);

        Response response = validateResponse(integrationRequest.request());
    }

    public Receipt receiptsSell(Receipt receipt) throws Exception {
        String receiptBody = new Gson().toJson(receipt, Receipt.class);
        RequestBody body = RequestBody.create(receiptBody, MediaType.parse("application/json"));

        RequestSender integrationRequest = new RequestSender(routes.sellReceipt());
        integrationRequest.setHeaders(headers);
        integrationRequest.setRequestBody(body);

        Response response = validateResponse(integrationRequest.request());

        return new Gson().fromJson(response.body().string(), Receipt.class);
    }

    public Receipt getReceipt(String receiptId) throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.getReceipt(receiptId));
        integrationRequest.setHeaders(headers);

        Response response = validateResponse(integrationRequest.request());

        return new Gson().fromJson(response.body().string(), Receipt.class);
    }

    public String getReceiptHtml(String receiptId, boolean isSimple) throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.getReceiptHtml(receiptId, isSimple));
        integrationRequest.setHeaders(headers);

        Response response = validateResponse(integrationRequest.request());
        return response.body().string();
    }

    public byte[] getReceiptPdf(String receiptId) throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.getReceiptPdf(receiptId));
        integrationRequest.setHeaders(headers);

        Response response = validateResponse(integrationRequest.request());
        return response.body().bytes();
    }

    public String getReceiptText(String receiptId, int width) throws Exception {
        if (width < 10) {
            width = 10;
        }

        RequestSender integrationRequest = new RequestSender(routes.getReceiptText(receiptId, width));
        integrationRequest.setHeaders(headers);

        Response response = validateResponse(integrationRequest.request());
        return response.body().string();
    }

    public byte[] getReceiptPng(String receiptId, PngWidths widths) throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.getReceiptPng(receiptId, widths));
        integrationRequest.setHeaders(headers);

        Response response = validateResponse(integrationRequest.request());
        return response.body().bytes();
    }

    public byte[] getReceiptQrCode(String receiptId) throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.getReceiptQrCode(receiptId));
        integrationRequest.setHeaders(headers);

        Response response = validateResponse(integrationRequest.request());
        return response.body().bytes();
    }

    public ArrayList<Tax> getAllTaxes() throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.getAllTaxes());
        integrationRequest.setHeaders(headers);

        Response response = validateResponse(integrationRequest.request());

        return new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<Tax>>(){}.getType());
    }

    public String pingTaxService() throws Exception {
        RequestSender integrationRequest = new RequestSender(routes.pingTaxService());
        integrationRequest.setHeaders(headers);

        Response response = validateResponse(integrationRequest.request());
        return response.body().string();
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
