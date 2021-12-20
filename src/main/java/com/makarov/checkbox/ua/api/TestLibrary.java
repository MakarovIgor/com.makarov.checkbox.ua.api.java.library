package com.makarov.checkbox.ua.api;

public class TestLibrary {

    public TestLibrary(Config config) throws Exception {
        System.out.println( config.get(Config.API_URL));
        System.out.println(config.get(Config.LOGIN));
        System.out.println(config.get(Config.PASSWORD));
        System.out.println(config.get(Config.LICENSE_KEY));
    }

    public void say() {
        System.out.println("Hello from TestLibrary");
    }
}
