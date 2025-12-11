package com.optivem.eshop.systemtest.dsl.external.tax.commands.base;

import com.optivem.eshop.systemtest.dsl.external.tax.driver.TaxDriver;
import com.optivem.testing.dsl.BaseCommand;
import com.optivem.testing.dsl.Context;

public abstract class BaseTaxCommand<TResponse, TVerification> extends BaseCommand<TaxDriver, TResponse, TVerification> {
    protected BaseTaxCommand(TaxDriver driver, Context context) {
        super(driver, context);
    }
}

