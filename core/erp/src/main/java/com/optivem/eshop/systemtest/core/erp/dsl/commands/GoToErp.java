package com.optivem.eshop.systemtest.core.erp.dsl.commands;

import com.optivem.eshop.systemtest.core.erp.driver.real.ErpRealDriver;
import com.optivem.testing.dsl.VoidResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.base.BaseErpCommand;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.base.ErpUseCaseResult;

public class GoToErp extends BaseErpCommand<Void, VoidResponseVerification<UseCaseContext>> {
    public GoToErp(ErpRealDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public ErpUseCaseResult<Void, VoidResponseVerification<UseCaseContext>> execute() {
        var result = driver.goToErp();
        return new ErpUseCaseResult<>(result, context, VoidResponseVerification::new);
    }
}

