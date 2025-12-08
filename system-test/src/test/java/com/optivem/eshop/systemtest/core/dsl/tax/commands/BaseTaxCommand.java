package com.optivem.eshop.systemtest.core.dsl.tax.commands;

import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.DslCommand;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

public abstract class BaseTaxCommand<T> implements DslCommand<T> {
    protected final TaxApiDriver driver;
    protected final DslContext context;

    protected BaseTaxCommand(TaxApiDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract T execute();
}

