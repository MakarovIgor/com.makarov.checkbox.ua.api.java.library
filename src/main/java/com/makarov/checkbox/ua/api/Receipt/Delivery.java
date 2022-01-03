package com.makarov.checkbox.ua.api.Receipt;

import java.util.ArrayList;

public class Delivery {
    private ArrayList<String> emails = new ArrayList<>();

    public Delivery() {

    }

    public Delivery(String email) {
        emails.add(email);
    }

    public Delivery(ArrayList<String> emails) {
        this.emails = emails;
    }
}
