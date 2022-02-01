package com.makarov.checkbox.ua.api.Requests;

import com.makarov.checkbox.ua.api.Requests.Routes.Route;
import okhttp3.*;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class Request {
    private String url;
    private RequestBody body = new RequestBody() {
        @Nullable
        @Override
        public MediaType contentType() {
            return MediaType.parse("application/json");
        }

        @Override
        public void writeTo(@NotNull BufferedSink bufferedSink) {

        }
    };
    private Methods method = Methods.GET;
    private Map<String, String> headers;

    public Request(Route route) {
        setUrl(route.url());
        setMethod(route.method());
    }

    public Request() {

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(Methods method) {
        this.method = method;
    }

    public void setRequestBody(RequestBody body) {
        if (body == null)
            return;
        this.body = body;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    private okhttp3.Request buildRequest() {
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();

        if (method == Methods.GET) {
            requestBuilder.get();
        } else if (method == Methods.POST) {
            requestBuilder.post(body);
        }

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        return requestBuilder.url(url).build();
    }

    private OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder.build();
    }

    public Response send() throws Exception {
        okhttp3.Request request = this.buildRequest();
        OkHttpClient client = this.buildOkHttpClient();

        return client.newCall(request).execute();
    }
}
