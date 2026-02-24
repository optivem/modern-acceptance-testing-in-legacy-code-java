package com.optivem.eshop.systemtest.dsl.core.system.erp.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.erp.ErpDriver;
import com.optivem.eshop.systemtest.dsl.core.system.erp.dsl.usecases.base.BaseErpCommand;
import com.optivem.eshop.systemtest.dsl.core.system.erp.dsl.usecases.base.ErpUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.VoidVerification;

public class GoToErp extends BaseErpCommand<Void, VoidVerification> {
    public GoToErp(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public ErpUseCaseResult<Void, VoidVerification> execute() {
        var result = driver.goToErp();
        return new ErpUseCaseResult<>(result, context, VoidVerification::new);
    }
}
