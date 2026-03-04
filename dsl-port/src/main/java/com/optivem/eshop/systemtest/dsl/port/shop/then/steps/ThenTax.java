package com.optivem.eshop.systemtest.dsl.port.shop.then.steps;

public interface ThenTax {
    ThenTax hasCountry(String country);

    ThenTax hasTaxRate(double taxRate);

    ThenTax hasTaxRateIsPositive();
}
