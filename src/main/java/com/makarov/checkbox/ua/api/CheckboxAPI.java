package com.makarov.checkbox.ua.api;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.makarov.checkbox.ua.api.Exceptions.InvalidCredentialsException;
import com.makarov.checkbox.ua.api.Exceptions.NotActiveShiftException;
import com.makarov.checkbox.ua.api.Exceptions.ValidationException;
import com.makarov.checkbox.ua.api.Models.Cashier;
import com.makarov.checkbox.ua.api.Models.PngWidths;
import com.makarov.checkbox.ua.api.Models.Receipt.Receipt;
import com.makarov.checkbox.ua.api.Models.Receipt.Tax;
import com.makarov.checkbox.ua.api.Models.Receipt.ServiceReceipt;
import com.makarov.checkbox.ua.api.Requests.Request;
import com.makarov.checkbox.ua.api.Requests.Routes.AllRoutes;
import com.makarov.checkbox.ua.api.Requests.Routes.Route;
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

        Response response = validateResponse(sendRequest(routes.cashierSignIn(), body));

        JsonObject tokenData = new Gson().fromJson(response.body().string(), JsonObject.class);

        setToken(tokenData.get("access_token").getAsString());
    }

    public void cashierSignOut() throws Exception {
        validateResponse(sendRequest(routes.cashierSignOut(), null));
    }

    public Cashier getCashierProfile() throws Exception {
        Response response = validateResponse(sendRequest(routes.cashierProfile(), null));
        return new Gson().fromJson(response.body().string(), Cashier.class);
    }

    public void openShift() throws Exception {
        Response response = validateResponse(sendRequest(routes.openShift(), null));
    }

    public void closeShift() throws Exception {
        Response response = validateResponse(sendRequest(routes.closeShift(), null));
    }

    public String createServiceReceipt(ServiceReceipt serviceReceipt) throws Exception {
        String receiptBody = new Gson().toJson(serviceReceipt, ServiceReceipt.class);
        RequestBody body = RequestBody.create(receiptBody, MediaType.parse("application/json"));

        Response response = validateResponse(sendRequest(routes.createServiceReceipt(), body));

        return response.body().string();
        //return new Gson().fromJson(response.body().string(), ServiceReceipt.class);
    }

    public Receipt receiptSell(Receipt receipt) throws Exception {
        String receiptBody = new Gson().toJson(receipt, Receipt.class);
        RequestBody body = RequestBody.create(receiptBody, MediaType.parse("application/json"));

        Response response = validateResponse(sendRequest(routes.sellReceipt(), body));

        return new Gson().fromJson(response.body().string(), Receipt.class);
    }

    public void sendReceiptToEmail(String receiptId, ArrayList<String> emails) throws Exception {
        String emailsJson = new Gson().toJson(emails);
        RequestBody body = RequestBody.create(emailsJson, MediaType.parse("application/json"));
        validateResponse(sendRequest(routes.sendReceiptToEmail(receiptId), body));
    }

    public Receipt getReceipt(String receiptId) throws Exception {
        Response response = validateResponse(sendRequest(routes.getReceipt(receiptId), null));
        return new Gson().fromJson(response.body().string(), Receipt.class);
    }

    public String getReceiptHtml(String receiptId, boolean isSimple) throws Exception {
        Response response = validateResponse(sendRequest(routes.getReceiptHtml(receiptId, isSimple), null));
        return response.body().string();
    }

    public byte[] getReceiptPdf(String receiptId) throws Exception {
        Response response = validateResponse(sendRequest(routes.getReceiptPdf(receiptId), null));
        return response.body().bytes();
    }

    public String getReceiptText(String receiptId, int width) throws Exception {
        if (width < 10) {
            width = 10;
        }

        Response response = validateResponse(sendRequest(routes.getReceiptText(receiptId, width), null));
        return response.body().string();
    }

    public byte[] getReceiptPng(String receiptId, PngWidths widths) throws Exception {
        Response response = validateResponse(sendRequest(routes.getReceiptPng(receiptId, widths), null));
        return response.body().bytes();
    }

    public byte[] getReceiptQrCode(String receiptId) throws Exception {
        Response response = validateResponse(sendRequest(routes.getReceiptQrCode(receiptId), null));
        return response.body().bytes();
    }

    public ArrayList<Tax> getAllTaxes() throws Exception {
        Response response = validateResponse(sendRequest(routes.getAllTaxes(), null));
        return new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<Tax>>(){}.getType());
    }

    public String pingTaxService() throws Exception {
        Response response = validateResponse(sendRequest(routes.pingTaxService(), null));
        return response.body().string();
    }

    protected Response validateResponse(Response response) throws Exception {
        if (!response.isSuccessful()) {
            ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
            JsonObject body = new Gson().fromJson(responseBody.string(), JsonObject.class);
            String message = body.get("message").getAsString();

            switch (response.code()) {
                case 400 -> throw new NotActiveShiftException(message);
                case 403 -> throw new InvalidCredentialsException(message);
                case 422 -> {
                    JsonArray detail = body.get("detail").getAsJsonArray();
                    message += ": " + detail.get(0).getAsJsonObject().get("msg").getAsString();
                    throw new ValidationException(message);
                }
            }

            if (!message.isEmpty()) {
                throw new Exception(message);
            }
        }
        return response;
    }

    protected void setToken(String token) {
        headers.put("Authorization", "Bearer " + token);
    }

    protected Response sendRequest(Route route, RequestBody body) throws Exception {
        Request request = new Request(route);
        request.setHeaders(headers);
        request.setRequestBody(body);
        return  request.send();
    }
}