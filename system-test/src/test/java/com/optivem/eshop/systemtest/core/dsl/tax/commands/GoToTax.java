package com.optivem.eshop.systemtest.core.dsl.tax.commands;

import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidVerifications;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.tax.commands.base.BaseTaxCommand;

public class GoToTax extends BaseTaxCommand<CommandResult<Void, VoidVerifications>> {
    public GoToTax(TaxApiDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidVerifications> execute() {
        var result = driver.goToTax();
        return new CommandResult<>(result, context, VoidVerifications::new);
    }
}

