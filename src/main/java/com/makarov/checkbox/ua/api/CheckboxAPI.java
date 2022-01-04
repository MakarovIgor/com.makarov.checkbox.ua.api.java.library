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
import com.makarov.checkbox.ua.api.Requests.Request;
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

        Request request = new Request(routes.cashierSignIn());
        request.setHeaders(headers);
        request.setRequestBody(body);

        Response response = validateResponse(request.send());

        JsonObject convertedObject = new Gson().fromJson(response.body().string(), JsonObject.class);

        setToken(convertedObject.get("access_token").getAsString());
    }

    public void cashierSignOut() throws Exception {
        Request request = new Request(routes.cashierSignOut());
        request.setHeaders(headers);

        validateResponse(request.send());
    }

    //TODO return Cashier
    public void getCashierProfile() throws Exception {
        Request request = new Request(routes.cashierProfile());
        request.setHeaders(headers);

        validateResponse(request.send());
    }

    public void openShift() throws Exception {
        Request request = new Request(routes.openShift());
        request.setHeaders(headers);

        Response response = validateResponse(request.send());
    }

    public void closeShift() throws Exception {
        Request request = new Request(routes.closeShift());
        request.setHeaders(headers);

        Response response = validateResponse(request.send());
    }

    public Receipt receiptsSell(Receipt receipt) throws Exception {
        String receiptBody = new Gson().toJson(receipt, Receipt.class);
        RequestBody body = RequestBody.create(receiptBody, MediaType.parse("application/json"));

        Request request = new Request(routes.sellReceipt());
        request.setHeaders(headers);
        request.setRequestBody(body);

        Response response = validateResponse(request.send());

        return new Gson().fromJson(response.body().string(), Receipt.class);
    }

    public Receipt getReceipt(String receiptId) throws Exception {
        Request request = new Request(routes.getReceipt(receiptId));
        request.setHeaders(headers);

        Response response = validateResponse(request.send());

        return new Gson().fromJson(response.body().string(), Receipt.class);
    }

    public String getReceiptHtml(String receiptId, boolean isSimple) throws Exception {
        Request request = new Request(routes.getReceiptHtml(receiptId, isSimple));
        request.setHeaders(headers);

        Response response = validateResponse(request.send());
        return response.body().string();
    }

    public byte[] getReceiptPdf(String receiptId) throws Exception {
        Request request = new Request(routes.getReceiptPdf(receiptId));
        request.setHeaders(headers);

        Response response = validateResponse(request.send());
        return response.body().bytes();
    }

    public String getReceiptText(String receiptId, int width) throws Exception {
        if (width < 10) {
            width = 10;
        }

        Request request = new Request(routes.getReceiptText(receiptId, width));
        request.setHeaders(headers);

        Response response = validateResponse(request.send());
        return response.body().string();
    }

    public byte[] getReceiptPng(String receiptId, PngWidths widths) throws Exception {
        Request request = new Request(routes.getReceiptPng(receiptId, widths));
        request.setHeaders(headers);

        Response response = validateResponse(request.send());
        return response.body().bytes();
    }

    public byte[] getReceiptQrCode(String receiptId) throws Exception {
        Request request = new Request(routes.getReceiptQrCode(receiptId));
        request.setHeaders(headers);

        Response response = validateResponse(request.send());
        return response.body().bytes();
    }

    public ArrayList<Tax> getAllTaxes() throws Exception {
        Request request = new Request(routes.getAllTaxes());
        request.setHeaders(headers);

        Response response = validateResponse(request.send());

        return new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<Tax>>(){}.getType());
    }

    public String pingTaxService() throws Exception {
        Request request = new Request(routes.pingTaxService());
        request.setHeaders(headers);

        Response response = validateResponse(request.send());
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
