package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.port.GivenCountryPort;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenCountry extends BaseGivenStep implements GivenCountryPort {
    private String country;
    private String taxRate;

    public GivenCountry(Given given) {
        super(given);
        withCode(DEFAULT_COUNTRY);
        withTaxRate(DEFAULT_TAX_RATE);
    }

    public GivenCountry withCode(String country) {
        this.country = country;
        return this;
    }

    public GivenCountry withTaxRate(String taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public GivenCountry withTaxRate(double taxRate) {
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