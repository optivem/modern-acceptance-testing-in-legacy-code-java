package com.optivem.eshop.systemtest.core.dsl.shop.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslCommand;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;

public abstract class BaseShopCommand<T> implements DslCommand<T> {
    protected final ShopDriver driver;
    protected final DslContext context;

    protected BaseShopCommand(ShopDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract T execute();
}
