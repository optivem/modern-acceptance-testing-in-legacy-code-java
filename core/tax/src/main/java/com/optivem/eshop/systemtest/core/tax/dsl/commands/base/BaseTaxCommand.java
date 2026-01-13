package com.optivem.eshop.systemtest.core.tax.dsl.commands.base;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.test.dsl.BaseUseCase;
import com.optivem.test.dsl.UseCaseContext;

public abstract class BaseTaxCommand<TResponse, TVerification> extends BaseUseCase<TaxDriver, UseCaseContext, TResponse, TaxErrorResponse, TVerification, TaxErrorVerification> {
    protected BaseTaxCommand(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

