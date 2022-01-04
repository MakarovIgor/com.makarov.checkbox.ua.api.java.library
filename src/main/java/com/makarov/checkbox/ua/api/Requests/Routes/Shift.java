package com.makarov.checkbox.ua.api.Requests.Routes;

import com.makarov.checkbox.ua.api.Requests.Methods;

public class Shift {
    String route = "shifts";
    String apiUrl;
    String fullRoute;

    public Shift(String apiUrlWithVersion) {
        this.apiUrl = apiUrlWithVersion;
        fullRoute = this.apiUrl + route;
    }

    public Route open() {
        return new Route(fullRoute, Methods.POST);
    }

    public Route close() {
        return new Route(fullRoute + "/close", Methods.POST);
    }
}
