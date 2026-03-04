package com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.base;

import com.optivem.eshop.systemtest.driver.port.tax.TaxDriver;
import com.optivem.eshop.systemtest.dsl.common.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.common.UseCaseContext;

public abstract class BaseTaxUseCase<TResponse, TVerification> extends BaseUseCase<TaxDriver, TResponse, TVerification> {
    protected BaseTaxUseCase(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}


