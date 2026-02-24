package com.optivem.eshop.systemtest.core.erp.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.driver.api.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.commons.dsl.BaseUseCase;
import com.optivem.commons.dsl.UseCaseContext;

public abstract class BaseErpCommand<TResponse, TVerification> extends BaseUseCase<ErpDriver, TResponse, ErpErrorResponse, TVerification, ErpErrorVerification> {
    protected BaseErpCommand(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}
