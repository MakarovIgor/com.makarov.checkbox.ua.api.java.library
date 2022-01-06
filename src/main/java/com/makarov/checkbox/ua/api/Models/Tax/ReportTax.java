package com.makarov.checkbox.ua.api.Models.Tax;

public class ReportTax extends AbstractTax {
    double rate;
    double extra_rate;
    int sell_sum;
    int return_sum;
    int sales_turnover;
    int returns_turnover;
    String setup_date;

    public ReportTax(Tax tax, double rate, double extraRate, int sellSum, int returnSum, int saleTurnoverSum, int returnTurnoverSum, String setupDate) {
        super(tax.code(), tax.label(), tax.symbol());
        this.rate = rate;
        this.extra_rate = extraRate;
        this.sell_sum = sellSum;
        this.return_sum = returnSum;
        this.sales_turnover = saleTurnoverSum;
        this.returns_turnover = returnTurnoverSum;
        this.setup_date = setupDate;
    }

    public double rate() {
        return rate;
    }

    public double extraRate() {
        return extra_rate;
    }

    public int sellSum() {
        return sell_sum;
    }

    public int returnSum() {
        return return_sum;
    }

    public int salesTurnoverSum() {
        return sales_turnover;
    }

    public int returnsTurnoverSum() {
        return returns_turnover;
    }

    public String setupDate() {
        return setup_date;
    }
}
