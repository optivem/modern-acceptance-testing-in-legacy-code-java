package com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.base;

import com.optivem.eshop.systemtest.driver.port.tax.TaxDriver;
import com.optivem.eshop.systemtest.dsl.core.app.shared.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.core.app.shared.UseCaseContext;

public abstract class BaseTaxCommand<TResponse, TVerification> extends BaseUseCase<TaxDriver, TResponse, TVerification> {
    protected BaseTaxCommand(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

