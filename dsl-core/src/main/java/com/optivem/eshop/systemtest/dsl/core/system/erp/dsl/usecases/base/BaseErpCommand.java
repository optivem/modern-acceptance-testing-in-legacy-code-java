package com.optivem.eshop.systemtest.dsl.core.system.erp.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.erp.ErpDriver;
import com.optivem.eshop.systemtest.driver.api.erp.dtos.error.ErpErrorResponse;
import com.optivem.eshop.systemtest.dsl.core.system.shared.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;

public abstract class BaseErpCommand<TResponse, TVerification> extends BaseUseCase<ErpDriver, TResponse, ErpErrorResponse, TVerification, ErpErrorVerification> {
    protected BaseErpCommand(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}
