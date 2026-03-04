package com.optivem.eshop.systemtest.dsl.core.app.erp.usecases.base;

import com.optivem.eshop.systemtest.driver.port.erp.ErpDriver;
import com.optivem.eshop.systemtest.dsl.core.app.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseContext;

public abstract class BaseErpUseCase<TResponse, TVerification> extends BaseUseCase<ErpDriver, TResponse, TVerification> {
    protected BaseErpUseCase(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

