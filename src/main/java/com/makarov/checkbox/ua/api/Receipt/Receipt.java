package com.makarov.checkbox.ua.api.Receipt;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Receipt {
    ArrayList<Good> goods;
    ArrayList<Payment> payments;

    String id = null;
    String cashier_name = null;
    String departament = null;
    Delivery delivery = null;
    boolean rounding = true;
    String barcode = "";
    String header = "";
    String footer = "";
    String related_receipt_id = null;
    String previous_receipt_id = null;
    boolean technical_return = false;
    String fiscal_code = null;
    ReceiptStatus status = null;

    private Receipt(ArrayList<Good> goods, ArrayList<Payment> payments) {
        this.goods = goods;
        this.payments = payments;
    }

    public String getId() {
        return id;
    }

    public ReceiptStatus getStatus() {
        return status;
    }

    public static class Builder {
        private Receipt receipt;

        public Builder(@NotNull ArrayList<Good> goods, @NotNull ArrayList<Payment> payments) {
            receipt = new Receipt(goods, payments);
        }

        public Builder setId(String id) {
            receipt.id = id;
            return this;
        }

        public Builder setCashierName(String cashierName) {
            receipt.cashier_name = cashierName;
            return this;
        }

        public Builder setDepartment(String departament) {
            receipt.departament = departament;
            return this;
        }

        public Builder setDelivery(Delivery delivery) {
            receipt.delivery = delivery;
            return this;
        }

        public Builder setRounding(boolean isRounding) {
            receipt.rounding = isRounding;
            return this;
        }

        public Builder setBarcode(String barcode) {
            receipt.barcode = barcode;
            return this;
        }

        public Builder setHeader(String header) {
            receipt.header = header;
            return this;
        }

        public Builder setFooter(String footer) {
            receipt.footer = footer;
            return this;
        }

        public Builder setRelatedReceiptId(String relatedReceiptId) {
            receipt.related_receipt_id = relatedReceiptId;
            return this;
        }

        public Builder setPreviousReceiptId(String previousReceiptId) {
            receipt.previous_receipt_id = previousReceiptId;
            return this;
        }

        public Builder setTechnicalReturn(boolean technicalReturn) {
            receipt.technical_return = technicalReturn;
            return this;
        }

        public Receipt build() {
            return receipt;
        }
    }
}
