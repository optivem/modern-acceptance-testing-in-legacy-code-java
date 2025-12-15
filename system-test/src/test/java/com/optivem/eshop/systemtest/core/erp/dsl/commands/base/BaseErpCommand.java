package com.optivem.eshop.systemtest.core.erp.dsl.commands.base;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.testing.dsl.BaseUseCase;
import com.optivem.testing.dsl.UseCaseContext;

public abstract class BaseErpCommand<TResponse, TVerification> extends BaseUseCase<ErpDriver, TResponse, TVerification> {
    protected BaseErpCommand(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

