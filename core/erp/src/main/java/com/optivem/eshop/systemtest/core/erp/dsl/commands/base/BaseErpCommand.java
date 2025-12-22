package com.optivem.eshop.systemtest.core.erp.dsl.commands.base;

import com.optivem.eshop.systemtest.core.commons.dsl.ErrorFailureVerification;
import com.optivem.eshop.systemtest.core.erp.driver.real.ErpRealDriver;
import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.testing.dsl.BaseUseCase;
import com.optivem.testing.dsl.UseCaseContext;

public abstract class BaseErpCommand<TResponse, TVerification> extends BaseUseCase<ErpRealDriver, UseCaseContext, TResponse, Error, TVerification, ErrorFailureVerification> {
    protected BaseErpCommand(ErpRealDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

