package com.optivem.eshop.systemtest.core.dsl.system.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;

public abstract class BaseCommand {
    protected final ShopDriver driver;
    protected final DslContext context;

    protected BaseCommand(ShopDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract void execute();
}
