package com.optivem.eshop.systemtest.core.tax.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.driver.api.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.commons.dsl.BaseUseCase;
import com.optivem.commons.dsl.UseCaseContext;

public abstract class BaseTaxCommand<TResponse, TVerification> extends BaseUseCase<TaxDriver, TResponse, TaxErrorResponse, TVerification, TaxErrorVerification> {
    protected BaseTaxCommand(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}
