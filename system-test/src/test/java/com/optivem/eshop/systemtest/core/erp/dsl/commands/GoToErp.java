package com.optivem.eshop.systemtest.core.erp.dsl.commands;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.testing.dsl.VoidVerification;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.base.BaseErpCommand;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.base.ErpUseCaseResult;

public class GoToErp extends BaseErpCommand<Void, VoidVerification<UseCaseContext>> {
    public GoToErp(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public ErpUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {
        var result = driver.goToErp();
        return new ErpUseCaseResult<>(result, context, VoidVerification::new);
    }
}

