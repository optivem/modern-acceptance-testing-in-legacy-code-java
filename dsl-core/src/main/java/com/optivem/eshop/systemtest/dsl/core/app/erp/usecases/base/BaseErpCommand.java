package com.optivem.eshop.systemtest.dsl.core.app.erp.usecases.base;

import com.optivem.eshop.systemtest.driver.port.erp.ErpDriver;
import com.optivem.eshop.systemtest.dsl.core.app.shared.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.core.app.shared.UseCaseContext;

public abstract class BaseErpCommand<TResponse, TVerification> extends BaseUseCase<ErpDriver, TResponse, TVerification> {
    protected BaseErpCommand(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

