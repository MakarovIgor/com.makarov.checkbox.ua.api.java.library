package com.makarov.checkbox.ua.api.Models.Payment;

import java.math.BigDecimal;

public class Payment {
    PaymentType type;
    int value;
    String label;

    public Payment(PaymentType type, BigDecimal sum) {
        this.type = type;
        this.value = (int) (sum.doubleValue() * 100);
    }

    public Payment(PaymentType type, BigDecimal sum, String label) {
        this.type = type;
        this.value = (int) (sum.doubleValue() * 100);
        this.label = label;
    }
}
