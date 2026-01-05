package com.optivem.eshop.systemtest.core.tax.dsl.commands;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.BaseTaxCommand;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.TaxUseCaseResult;
import com.optivem.eshop.systemtest.core.tax.dsl.verifications.GetTaxVerification;
import com.optivem.testing.dsl.ExternalSystemMode;
import com.optivem.testing.dsl.UseCaseContext;

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
        var country = context.getParamValueOrLiteral(countryValueOrAlias);

        var result = driver.getTaxRate(country);

        return new TaxUseCaseResult<>(result, context, GetTaxVerification::new);
    }
}


