package com.makarov.checkbox.ua.api.Receipt;

public class Tax {
    int code;
    String label;
    char symbol;

    public Tax(int code, String label, char symbol) {
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
