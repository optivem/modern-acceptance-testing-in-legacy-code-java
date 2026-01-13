package com.optivem.eshop.systemtest.core.erp.dsl.commands.base;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.commons.dsl.BaseUseCase;
import com.optivem.commons.dsl.UseCaseContext;

public abstract class BaseErpCommand<TResponse, TVerification> extends BaseUseCase<ErpDriver, UseCaseContext, TResponse, ErpErrorResponse, TVerification, ErpErrorVerification> {
    protected BaseErpCommand(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}