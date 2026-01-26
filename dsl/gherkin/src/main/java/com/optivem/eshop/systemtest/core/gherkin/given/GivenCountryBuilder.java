package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.commons.util.Converter;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenCountryBuilder extends BaseGivenBuilder {
    private String country;
    private String taxRate;

    public GivenCountryBuilder(GivenClause givenClause) {
        super(givenClause);
        withCode(DEFAULT_COUNTRY);
        withTaxRate(DEFAULT_TAX_RATE);
    }

    public GivenCountryBuilder withCode(String country) {
        this.country = country;
        return this;
    }

    public GivenCountryBuilder withTaxRate(String taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public GivenCountryBuilder withTaxRate(double taxRate) {
        return withTaxRate(Converter.fromDouble(taxRate));
    }

    @Override
    void execute(SystemDsl app) {
        app.tax().returnsTaxRate()
                .country(country)
                .taxRate(taxRate)
                .execute()
                .shouldSucceed();
    }
}

