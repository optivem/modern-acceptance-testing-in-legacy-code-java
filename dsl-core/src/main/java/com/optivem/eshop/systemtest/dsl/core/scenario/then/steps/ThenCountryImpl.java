package com.optivem.eshop.systemtest.dsl.core.scenario.then.steps;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.GetTaxVerification;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultContext;
import com.optivem.eshop.systemtest.dsl.core.shared.VoidVerification;
import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenCountry;

public class ThenCountryImpl extends BaseThenStep<Void, VoidVerification> implements ThenCountry {
    private final GetTaxVerification verification;

    public ThenCountryImpl(AppDsl app, ExecutionResultContext executionResult, GetTaxVerification verification) {
        super(app, executionResult, null);
        this.verification = verification;
    }

    @Override
    public ThenCountryImpl hasCountry(String country) {
        verification.country(country);
        return this;
    }

    @Override
    public ThenCountryImpl hasTaxRate(double taxRate) {
        verification.taxRate(taxRate);
        return this;
    }

    @Override
    public ThenCountryImpl hasTaxRateIsPositive() {
        verification.taxRateIsPositive();
        return this;
    }

    @Override
    public ThenCountryImpl and() {
        return this;
    }
}

