package com.optivem.eshop.systemtest.core.tax.dsl.commands.base;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.testing.dsl.BaseUseCase;
import com.optivem.testing.dsl.UseCaseContext;

public abstract class BaseTaxCommand<TResponse, TVerification> extends BaseUseCase<TaxDriver, UseCaseContext, TResponse, TaxError, TVerification, TaxErrorVerification> {
    protected BaseTaxCommand(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

