package com.optivem.eshop.systemtest.dsl.api;

public interface GivenCountryPort extends GivenStepPort {
    GivenCountryPort withCode(String country);

    GivenCountryPort withTaxRate(String taxRate);

    GivenCountryPort withTaxRate(double taxRate);

}
