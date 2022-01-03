package com.makarov.checkbox.ua.api;

import okhttp3.*;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.*;
import java.io.IOException;
import java.util.Map;

public class IntegrationRequest {
    private String url;
    private RequestBody body = new RequestBody() {
        @Nullable
        @Override
        public MediaType contentType() {
            return MediaType.parse("application/json");
        }

        @Override
        public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {

        }
    };
    private String method = "GET";
    private Map<String, String> headers;

    public IntegrationRequest(String url) {
        this.url = url;
    }

    public IntegrationRequest() {

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRequestBody(RequestBody body) {
        this.body = body;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    private Request buildRequest() throws Exception {
        Request.Builder requestBuilder = new okhttp3.Request.Builder();
        String lowerMethod = method.toLowerCase();

        if (lowerMethod.equals("get")) {
            requestBuilder.get();
        } else if (lowerMethod.equals("post")) {
            requestBuilder.post(body);
        } else if (lowerMethod.equals("put")) {
            if (body != null)
                requestBuilder.put(body);
            else
                throw new Exception("RequestBody must be not empty in PUT query");
        }

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        return requestBuilder.url(url).build();
    }

    private OkHttpClient buildOkHttpClient() {
        OkHttpClient client = null;
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (url.contains("https://")) {
                TrustManager[] trustAllCerts = getTrustAllCerts();
                final SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            }
            client = builder.build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return client;
    }

    public Response request() throws Exception {
        okhttp3.Request request = this.buildRequest();
        OkHttpClient client = this.buildOkHttpClient();

        return client.newCall(request).execute();
    }

    private TrustManager[] getTrustAllCerts() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }
}
