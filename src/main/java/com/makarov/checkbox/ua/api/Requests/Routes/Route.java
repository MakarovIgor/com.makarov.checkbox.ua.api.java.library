package com.makarov.checkbox.ua.api.Requests.Routes;

import com.makarov.checkbox.ua.api.Requests.Methods;

public class Route {
    String url;
    Methods method = Methods.GET;

    public Route(String url) {
        this.url = url;
    }

    public Route(String url, Methods method) {
        this.url = url;
        this.method = method;
    }

    public Methods method() {
        return method;
    }

    public String url() {
        return url;
    }
}
