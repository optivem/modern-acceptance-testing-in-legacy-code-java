package com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.base;

import com.optivem.eshop.systemtest.driver.port.tax.TaxDriver;
import com.optivem.eshop.systemtest.dsl.core.app.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseContext;

public abstract class BaseTaxUseCase<TResponse, TVerification> extends BaseUseCase<TaxDriver, TResponse, TVerification> {
    protected BaseTaxUseCase(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

