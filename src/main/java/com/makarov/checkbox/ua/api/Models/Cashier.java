package com.makarov.checkbox.ua.api.Models;

public class Cashier {
    private String id;
    private String full_name;
    private String nin;
    private String key_id;
    private String signature_type;

    public String id() {
        return id;
    }

    public String fullName() {
        return full_name;
    }

    public String nin() {
        return nin;
    }

    public String keyId() {
        return key_id;
    }

    public String signatureType() {
        return signature_type;
    }
}
