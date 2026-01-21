package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.commons.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenCountryBuilder extends BaseGivenBuilder {
    private static final Logger log = LoggerFactory.getLogger(GivenCountryBuilder.class);
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

