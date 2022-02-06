package com.makarov.checkbox.ua.api.Models.Receipt;

import com.makarov.checkbox.ua.api.Models.Discount.Discount;
import com.makarov.checkbox.ua.api.Models.Tax.Tax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Good {
    Map<String, Object> good = new LinkedHashMap<>();
    int quantity;
    boolean is_return = false;
    ArrayList<Discount> discounts = new ArrayList<>();

    private Good(String name, String code, int price, int quantity, ArrayList<Tax> taxes) {
        good.put("code", code);
        good.put("name", name);
        good.put("price", price);
        good.put("tax", prepareTaxes(taxes));

        this.quantity = quantity;
    }

    public static class Builder {
        private Good good;

        public Builder(String name, String code, BigDecimal price, BigDecimal quantity, ArrayList<Tax> taxes) {
            good = new Good(name, code, (int) (price.doubleValue() * 100), (int) (quantity.doubleValue() * 1000), taxes);
        }

        public Builder setDiscount(Discount discount) {
            good.discounts.add(discount);
            return this;
        }

        public Builder isReturn() {
            good.is_return = true;
            return this;
        }

        public Builder setUktzed(String uktzed) {
            good.good.put("uktzed", uktzed);
            return this;
        }

        public Builder setHeader(String header) {
            good.good.put("header", header);
            return this;
        }

        public Builder setFooter(String footer) {
            good.good.put("footer", footer);
            return this;
        }

        public Builder setBarcode(String barcode) {
            good.good.put("barcode", barcode);
            return this;
        }

        public Good build() {
            return good;
        }
    }

    private ArrayList<Integer> prepareTaxes(ArrayList<Tax> taxes) {
        ArrayList<Integer> goodTaxes = new ArrayList<>();
        for (Tax tax : taxes) {
            goodTaxes.add(tax.code());
        }
        return goodTaxes;
    }
}
