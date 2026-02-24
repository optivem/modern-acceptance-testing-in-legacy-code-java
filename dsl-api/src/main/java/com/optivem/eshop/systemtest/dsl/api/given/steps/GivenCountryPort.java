package com.optivem.eshop.systemtest.dsl.api.given.steps;

public interface GivenCountryPort extends GivenStepPort {
    GivenCountryPort withCode(String country);

    GivenCountryPort withTaxRate(String taxRate);

    GivenCountryPort withTaxRate(double taxRate);

}
