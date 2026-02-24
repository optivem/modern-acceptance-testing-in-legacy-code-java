package com.optivem.eshop.systemtest.core.gherkin.port;

public interface GivenCountryPort extends GivenStep {
    GivenCountryPort withCode(String country);

    GivenCountryPort withTaxRate(String taxRate);

    GivenCountryPort withTaxRate(double taxRate);

}
