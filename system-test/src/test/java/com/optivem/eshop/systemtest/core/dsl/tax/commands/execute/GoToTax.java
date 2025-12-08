package com.optivem.eshop.systemtest.core.dsl.tax.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.tax.commands.BaseTaxCommand;

public class GoToTax extends BaseTaxCommand<Void> {
    public static final String COMMAND_NAME = "GoToTax";

    public GoToTax(TaxApiDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public Void execute() {
        var result = driver.goToTax();
        context.results().registerResult(COMMAND_NAME, result);
        return null;
    }
}

