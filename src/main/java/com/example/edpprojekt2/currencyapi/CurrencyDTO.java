package com.example.edpprojekt2.currencyapi;

import java.util.Map;

public class CurrencyDTO {
    private String base;
    private Map<String, Float> rates;

    public CurrencyDTO(String base, Map<String, Float> rates) {
        this.base = base;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setRates(Map<String, Float> rates) {
        this.rates = rates;
    }
}
