package com.optivem.eshop.systemtest.core.gherkin.port;

public interface GivenCountryPort {
    GivenCountryPort withCode(String country);

    GivenCountryPort withTaxRate(String taxRate);

    GivenCountryPort withTaxRate(double taxRate);

    GivenPort and();

    WhenPort when();
}
