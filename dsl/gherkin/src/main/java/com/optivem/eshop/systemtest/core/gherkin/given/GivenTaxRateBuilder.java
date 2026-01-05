package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenTaxRateBuilder extends BaseGivenBuilder {
    private static final Logger log = LoggerFactory.getLogger(GivenTaxRateBuilder.class);
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
        long start = System.currentTimeMillis();
        app.tax().returnsTaxRate()
                .country(country)
                .taxRate(taxRate)
                .execute()
                .shouldSucceed();
        long elapsed = System.currentTimeMillis() - start;
        log.info("[PERF] GivenTaxRateBuilder.execute took {}ms", elapsed);
    }
}

