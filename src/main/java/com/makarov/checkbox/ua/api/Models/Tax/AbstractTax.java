package com.makarov.checkbox.ua.api.Models.Tax;

public class AbstractTax {
    int code;
    String label;
    char symbol;

    public AbstractTax(int code, String label, char symbol) {
        this.code = code;
        this.label = label;
        this.symbol = symbol;
    }

    public int code() {
        return code;
    }

    public String label() {
        return label;
    }

    public char symbol() {
        return symbol;
    }
}
