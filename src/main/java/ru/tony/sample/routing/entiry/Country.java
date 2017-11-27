package ru.tony.sample.routing.entiry;

import java.util.List;

public class Country {

    private String name;
    private List<Currency> currencies;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}
