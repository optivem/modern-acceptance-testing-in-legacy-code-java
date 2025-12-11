package com.optivem.eshop.systemtest.dsl.shop.commands.base;

import com.optivem.eshop.systemtest.dsl.shop.drivers.ShopDriver;
import com.optivem.testing.dsl.BaseCommand;
import com.optivem.testing.dsl.Context;

public abstract class BaseShopCommand<TResponse, TVerification> extends BaseCommand<ShopDriver, TResponse, TVerification> {
    protected BaseShopCommand(ShopDriver driver, Context context) {
        super(driver, context);
    }
}

