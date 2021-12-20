package com.makarov.checkbox.ua.api;

import java.util.LinkedHashMap;
import java.util.Map;

public class Config {
    public static final String API_URL = "apiUrl";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String LICENSE_KEY = "licenseKey";

    private Map<String, String> configData = new LinkedHashMap<>();

    public Config(String apiUrl, String login, String password, String licenseKey) {
        configData.put(API_URL, apiUrl);
        configData.put(LOGIN, login);
        configData.put(PASSWORD, password);
        configData.put(LICENSE_KEY, licenseKey);
    }

    public String get(String configName) throws Exception {
        if (configData.containsKey(configName)) {
            return configData.get(configName);
        }
        throw new Exception("Name " + configName + " not found in config class");
    }
}
