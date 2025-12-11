package com.optivem.eshop.systemtest.dsl.external.erp.commands;

import com.optivem.eshop.systemtest.dsl.external.erp.driver.ErpDriver;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.VoidVerification;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.dsl.external.erp.commands.base.BaseErpCommand;

public class GoToErp extends BaseErpCommand<Void, VoidVerification> {
    public GoToErp(ErpDriver driver, Context context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var result = driver.goToErp();
        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

