package com.makarov.checkbox.ua.api.Models.Payment;

public class Payment {
    PaymentType type;
    int value;
    String label;

    public Payment(PaymentType type, int sum) {
        this.type = type;
        this.value = sum;
    }

    public Payment(PaymentType type, int sum, String label) {
        this.type = type;
        this.value = sum;
        this.label = label;
    }
}
