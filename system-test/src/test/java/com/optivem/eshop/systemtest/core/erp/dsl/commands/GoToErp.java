package com.optivem.eshop.systemtest.core.erp.dsl.commands;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.testing.dsl.UseCaseResult;
import com.optivem.testing.dsl.UseCaseVoidSuccessVerification;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.base.BaseErpCommand;

public class GoToErp extends BaseErpCommand<Void, UseCaseVoidSuccessVerification> {
    public GoToErp(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public UseCaseResult<Void, UseCaseVoidSuccessVerification> execute() {
        var result = driver.goToErp();
        return new UseCaseResult<>(result, context, UseCaseVoidSuccessVerification::new);
    }
}

