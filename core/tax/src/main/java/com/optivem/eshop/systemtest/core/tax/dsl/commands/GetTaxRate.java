package com.optivem.eshop.systemtest.core.tax.dsl.commands;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.eshop.systemtest.core.tax.dsl.verifications.GetTaxVerification;
import com.optivem.testing.dsl.ExternalSystemMode;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.BaseTaxCommand;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.TaxUseCaseResult;

public class GetTaxRate extends BaseTaxCommand<GetTaxResponse, GetTaxVerification> {
    private static final String DEFAULT_COUNTRY = "DEFAULT_COUNTRY";

    private String countryValueOrAlias;

    public GetTaxRate(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
        country(DEFAULT_COUNTRY);
    }

    public GetTaxRate country(String countryValueOrAlias) {
        this.countryValueOrAlias = countryValueOrAlias;
        return this;
    }

    @Override
    public TaxUseCaseResult<GetTaxResponse, GetTaxVerification> execute() {
        var country = getCountry();

        var request = GetTaxRequest.builder()
                .country(country)
                .build();

        var result = driver.getTax(request);

        return new TaxUseCaseResult<>(result, context, GetTaxVerification::new);
    }

    private String getCountry() {
        if(context.getExternalSystemMode() == ExternalSystemMode.STUB) {
            return context.getParamValue(countryValueOrAlias);
        } else if(context.getExternalSystemMode() == ExternalSystemMode.REAL) {
            return countryValueOrAlias;
        } else {
            throw new IllegalStateException("Unsupported external system mode: " + context.getExternalSystemMode());
        }
    }
}


