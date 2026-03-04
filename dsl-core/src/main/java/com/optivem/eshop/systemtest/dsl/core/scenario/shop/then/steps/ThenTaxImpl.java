package com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps;

import com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.GetTaxVerification;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenTax;

public class ThenTaxImpl implements ThenTax {
    private final GetTaxVerification verification;

    public ThenTaxImpl(GetTaxVerification verification) {
        this.verification = verification;
    }

    @Override
    public ThenTax hasCountry(String country) {
        verification.country(country);
        return this;
    }

    @Override
    public ThenTax hasTaxRate(double taxRate) {
        verification.taxRate(taxRate);
        return this;
    }

    @Override
    public ThenTax hasTaxRateIsPositive() {
        verification.taxRateIsPositive();
        return this;
    }
}
