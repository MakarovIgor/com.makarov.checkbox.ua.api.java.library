package com.makarov.checkbox.ua.api.Receipt;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Good {
    Map<String, Object> good = new LinkedHashMap<>();
    int quantity;
    boolean is_return = false;
    ArrayList<Discount> discounts = new ArrayList<>();

    public Good(String name, String code, int price, int quantity, ArrayList<Tax> taxes, boolean isReturn) {
        good.put("code", code);
        good.put("name", name);
        good.put("uktzed", null);
        good.put("price", price);
        good.put("barcode", "");
        good.put("footer", "");
        good.put("header", "");
        good.put("tax", prepareTaxes(taxes));

        this.is_return = isReturn;
        this.quantity = quantity;
    }

    private ArrayList<Integer> prepareTaxes(ArrayList<Tax> taxes) {
        ArrayList<Integer> goodTaxes = new ArrayList<>();
        for (Tax tax : taxes) {
            goodTaxes.add(tax.code);
        }
        return goodTaxes;
    }
}
