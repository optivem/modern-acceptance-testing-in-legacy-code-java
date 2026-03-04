package com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps;

import com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.GetTaxVerification;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenCountry;

public class ThenCountryImpl implements ThenCountry {
    private final GetTaxVerification verification;

    public ThenCountryImpl(GetTaxVerification verification) {
        this.verification = verification;
    }

    @Override
    public ThenCountry hasCountry(String country) {
        verification.country(country);
        return this;
    }

    @Override
    public ThenCountry hasTaxRate(double taxRate) {
        verification.taxRate(taxRate);
        return this;
    }

    @Override
    public ThenCountry hasTaxRateIsPositive() {
        verification.taxRateIsPositive();
        return this;
    }
}
