package com.optivem.eshop.systemtest.core.dsl.erp.commands;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslCommand;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;

public abstract class BaseErpCommand<T> implements DslCommand<T> {
    protected final ErpApiDriver driver;
    protected final DslContext context;

    protected BaseErpCommand(ErpApiDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract T execute();
}

