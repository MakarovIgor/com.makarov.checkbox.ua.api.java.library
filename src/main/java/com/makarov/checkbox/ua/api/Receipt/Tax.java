package com.makarov.checkbox.ua.api.Receipt;

public class Tax {
    int code;
    char symbol;

    public Tax(int code, String label, char symbol) {
        this.code = code;
        this.symbol = symbol;
    }

    public int code() {
        return code;
    }
}
