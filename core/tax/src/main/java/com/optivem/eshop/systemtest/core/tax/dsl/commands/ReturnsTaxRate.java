package com.optivem.eshop.systemtest.core.tax.dsl.commands;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.testing.dsl.ExternalSystemMode;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.VoidResponseVerification;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.BaseTaxCommand;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.TaxUseCaseResult;

public class ReturnsTaxRate extends BaseTaxCommand<Void, VoidResponseVerification<UseCaseContext>> {
    private static final String DEFAULT_COUNTRY = "DEFAULT_COUNTRY";
    private static final double DEFAULT_TAX_RATE = 0.07;

    private String countryAlias;
    private String taxRate;

    public ReturnsTaxRate(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
        country(DEFAULT_COUNTRY);
        taxRate(DEFAULT_TAX_RATE);
    }

    public ReturnsTaxRate country(String countryAlias) {
        this.countryAlias = countryAlias;
        return this;
    }

    public ReturnsTaxRate taxRate(String taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public ReturnsTaxRate taxRate(double taxRate) {
        return taxRate(String.valueOf(taxRate));
    }

    @Override
    public TaxUseCaseResult<Void, VoidResponseVerification<UseCaseContext>> execute() {
        var country = getCountry();

        var request = ReturnsTaxRateRequest.builder()
                .country(country)
                .taxRate(taxRate)
                .build();

        var result = driver.returnsTaxRate(request);

        return new TaxUseCaseResult<>(result, context, VoidResponseVerification::new);
    }

    private String getCountry() {
        if(context.getExternalSystemMode() == ExternalSystemMode.STUB) {
            return context.getParamValue(countryAlias);
        } else if(context.getExternalSystemMode() == ExternalSystemMode.REAL) {
            return countryAlias;
        } else {
            throw new IllegalStateException("Unsupported external system mode: " + context.getExternalSystemMode());
        }
    }
}

