package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.BaseShopCommand;

public class GoToShop extends BaseShopCommand<Void> {
    public static final String COMMAND_NAME = "GoToShop";

    public GoToShop(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public Void execute() {
        var result = driver.goToShop();
        context.results().registerResult(COMMAND_NAME, result);
        return null;
    }
}

