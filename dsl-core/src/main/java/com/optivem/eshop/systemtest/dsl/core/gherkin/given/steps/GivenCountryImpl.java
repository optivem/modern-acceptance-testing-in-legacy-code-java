package com.optivem.eshop.systemtest.dsl.core.gherkin.given.steps;

import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenCountryPort;

import static com.optivem.eshop.systemtest.dsl.core.gherkin.GherkinDefaults.*;

public class GivenCountryImpl extends BaseGivenStep implements GivenCountryPort {
    private String country;
    private String taxRate;

    public GivenCountryImpl(GivenImpl given) {
        super(given);
        withCode(DEFAULT_COUNTRY);
        withTaxRate(DEFAULT_TAX_RATE);
    }

    public GivenCountryImpl withCode(String country) {
        this.country = country;
        return this;
    }

    public GivenCountryImpl withTaxRate(String taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public GivenCountryImpl withTaxRate(double taxRate) {
        return withTaxRate(Converter.fromDouble(taxRate));
    }

    @Override
    public void execute(SystemDsl app) {
        app.tax().returnsTaxRate()
                .country(country)
                .taxRate(taxRate)
                .execute()
                .shouldSucceed();
    }
}