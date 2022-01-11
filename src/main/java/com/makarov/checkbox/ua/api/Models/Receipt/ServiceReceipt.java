package com.makarov.checkbox.ua.api.Models.Receipt;

import com.makarov.checkbox.ua.api.Models.Payment.Payment;

public class ServiceReceipt {
    String id;
    Payment payment;

    public ServiceReceipt(Payment payment) {
        this.payment = payment;
    }

    public String getId() {
        return id;
    }
}
