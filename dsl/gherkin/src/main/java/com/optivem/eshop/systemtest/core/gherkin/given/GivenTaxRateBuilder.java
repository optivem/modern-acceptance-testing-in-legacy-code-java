package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenTaxRateBuilder extends BaseGivenBuilder {
    private String country;
    private String taxRate;

    public GivenTaxRateBuilder(GivenClause givenClause) {
        super(givenClause);
        withCountry(DEFAULT_COUNTRY);
        withTaxRate(DEFAULT_TAX_RATE);
    }

    public GivenTaxRateBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public GivenTaxRateBuilder withTaxRate(String taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public GivenTaxRateBuilder withTaxRate(double taxRate) {
        return withTaxRate(String.valueOf(taxRate));
    }

    void execute(SystemDsl app) {
        app.tax().returnsTaxRate()
                .country(country)
                .taxRate(taxRate)
                .execute()
                .shouldSucceed();
    }
}

