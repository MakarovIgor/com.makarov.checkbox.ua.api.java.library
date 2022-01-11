package com.makarov.checkbox.ua.api.Models.Discount;

public class Discount {
    DiscountType type;
    DiscountMode mode;
    int value;
    int sum;

    public Discount(DiscountType type, DiscountMode mode, int value) {
        this.type = type;
        this.mode = mode;
        this.value = value;
    }
}
