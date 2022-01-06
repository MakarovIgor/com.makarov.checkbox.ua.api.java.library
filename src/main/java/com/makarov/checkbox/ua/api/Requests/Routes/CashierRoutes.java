package com.makarov.checkbox.ua.api.Requests.Routes;

import com.makarov.checkbox.ua.api.Requests.Methods;

public class CashierRoutes {
    String apiUrl;
    String route = "cashier";
    String fullRoute;

    public CashierRoutes(String apiUrlWithVersion) {
        this.apiUrl = apiUrlWithVersion;
        fullRoute = this.apiUrl + route;
    }

    public Route signIn() {
        return new Route(fullRoute + "/signin", Methods.POST);
    }

    public Route signOut() {
        return new Route(fullRoute + "/signout", Methods.POST);
    }

    public Route getProfile() {
        return new Route(fullRoute + "/me");
    }

    public Route getCashierShift() {
        return new Route(fullRoute + "/shift");
    }
}

