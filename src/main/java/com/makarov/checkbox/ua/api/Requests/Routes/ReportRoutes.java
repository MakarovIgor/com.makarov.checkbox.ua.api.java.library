package com.makarov.checkbox.ua.api.Requests.Routes;

import com.makarov.checkbox.ua.api.Requests.Methods;

public class ReportRoutes {
    String route = "reports";
    String apiUrl;
    String fullRoute;

    public ReportRoutes(String apiUrlWithVersion) {
        this.apiUrl = apiUrlWithVersion;
        fullRoute = this.apiUrl + route + "/";
    }

    public Route createXReport() {
        return new Route(fullRoute, Methods.POST);
    }

    public Route getReport(String reportId) {
        return new Route(fullRoute + reportId);
    }

    public Route getReportText(String reportId) {
        return new Route(fullRoute + reportId + "/text");
    }

    public Route getReportText(String reportId, int widthInCharacters) {
        int width = (widthInCharacters > 250) ? 250 : Math.max(widthInCharacters, 10);
        return new Route(fullRoute + reportId + "/text?width" + width);
    }


}
