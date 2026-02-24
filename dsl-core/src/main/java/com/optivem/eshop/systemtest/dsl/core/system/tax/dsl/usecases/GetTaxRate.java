package com.optivem.eshop.systemtest.dsl.core.system.tax.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.tax.TaxDriver;
import com.optivem.eshop.systemtest.driver.api.tax.dtos.GetTaxResponse;
import com.optivem.eshop.systemtest.dsl.core.system.tax.dsl.usecases.base.BaseTaxCommand;
import com.optivem.eshop.systemtest.dsl.core.system.tax.dsl.usecases.base.TaxUseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;

public class GetTaxRate extends BaseTaxCommand<GetTaxResponse, GetTaxVerification> {
    private String countryValueOrAlias;

    public GetTaxRate(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
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
