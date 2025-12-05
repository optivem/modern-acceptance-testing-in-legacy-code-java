package com.optivem.eshop.systemtest.core.dsl.system.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslCommand;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;

public abstract class BaseShopCommand implements DslCommand {
    protected final ShopDriver driver;
    protected final DslContext context;

    protected BaseShopCommand(ShopDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract void execute();
}
