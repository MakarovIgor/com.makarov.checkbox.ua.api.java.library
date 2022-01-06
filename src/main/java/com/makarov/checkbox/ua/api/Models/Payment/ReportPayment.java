package com.makarov.checkbox.ua.api.Models.Payment;

public class ReportPayment {
    String id;
    int code;
    PaymentType type;
    String label;
    int sell_sum;
    int return_sum;
    int service_in;
    int service_out;

    public ReportPayment(String id, PaymentType type, String label, int sellSum, int returnSum, int serviceInSum, int serviceOutSum) {
        this.id = id;
        this.type = type;
        this.label = label;
        this.sell_sum = sellSum;
        this.return_sum = returnSum;
        this.service_in = serviceInSum;
        this.service_out = serviceOutSum;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String id() {
        return id;
    }

    public int code() {
        return code;
    }

    public int sellSum() {
        return sell_sum;
    }

    public int returnSum() {
        return return_sum;
    }

    public int serviceInSum() {
        return service_in;
    }

    public int serviceOutSum() {
        return service_out;
    }
}
