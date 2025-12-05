package com.optivem.eshop.systemtest.core.dsl.system.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.BaseCommand;

public class GoToShopCommand extends BaseCommand {

    public GoToShopCommand(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public void execute() {
        var result = driver.goToShop();
        context.results().register("goToShop", result);
    }
}

