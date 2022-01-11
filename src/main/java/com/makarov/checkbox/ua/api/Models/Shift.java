package com.makarov.checkbox.ua.api.Models;

import com.makarov.checkbox.ua.api.Models.Tax.Tax;

import java.util.ArrayList;

public class Shift {
    String id;
    int serial;
    Report z_report;
    ArrayList<Tax> taxes;
    String status;

    public String id() {
        return id;
    }

    public int serial() {
        return serial;
    }

    public Report report() {
        return z_report;
    }

    public ArrayList<Tax> taxes() {
        return taxes;
    }

    public String status() {
        return status;
    }
}
