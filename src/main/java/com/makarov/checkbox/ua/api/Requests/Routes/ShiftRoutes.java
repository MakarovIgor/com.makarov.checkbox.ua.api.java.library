package com.makarov.checkbox.ua.api.Requests.Routes;

import com.makarov.checkbox.ua.api.Requests.Methods;

public class ShiftRoutes {
    String route = "shifts";
    String apiUrl;
    String fullRoute;

    public ShiftRoutes(String apiUrlWithVersion) {
        this.apiUrl = apiUrlWithVersion;
        fullRoute = this.apiUrl + route;
    }

    public Route open() {
        return new Route(fullRoute, Methods.POST);
    }

    public Route close() {
        return new Route(fullRoute + "/close", Methods.POST);
    }

    public Route getShift(String shiftId) {
        return new Route(fullRoute + "/" + shiftId);
    }

    public Route getShifts() {
        return new Route(fullRoute);
    }
}
