package com.makarov.checkbox.ua.api.Requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.makarov.checkbox.ua.api.Exceptions.InvalidCredentialsException;
import com.makarov.checkbox.ua.api.Exceptions.NotActiveShiftException;
import com.makarov.checkbox.ua.api.Exceptions.ValidationException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseValidator {
    public static void validate(Response response) throws Exception {
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
    }
}
