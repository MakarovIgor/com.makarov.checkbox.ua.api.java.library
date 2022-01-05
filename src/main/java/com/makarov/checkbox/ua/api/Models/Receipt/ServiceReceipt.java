package com.makarov.checkbox.ua.api.Models.Receipt;

public class ServiceReceipt {
    String id;
    Payment payment;
    String fiscal_code;

    public ServiceReceipt(Payment payment) {
        this.payment = payment;
    }
}
