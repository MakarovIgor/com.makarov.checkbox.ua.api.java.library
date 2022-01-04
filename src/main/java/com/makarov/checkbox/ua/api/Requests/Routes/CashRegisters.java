package com.makarov.checkbox.ua.api.Requests.Routes;

import com.makarov.checkbox.ua.api.Requests.Methods;

public class CashRegisters {
    String route = "cash-registers";
    String apiUrl;
    String fullRoute;

    public CashRegisters(String apiUrlWithVersion) {
        this.apiUrl = apiUrlWithVersion;
        fullRoute = this.apiUrl + route;
    }

    public Route pingTaxService() {
        return new Route(fullRoute + "/ping-tax-service", Methods.POST);
    }
}
