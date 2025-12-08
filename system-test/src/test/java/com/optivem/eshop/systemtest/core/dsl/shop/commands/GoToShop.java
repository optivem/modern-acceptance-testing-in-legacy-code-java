package com.optivem.eshop.systemtest.core.dsl.shop.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidVerifications;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.base.BaseShopCommand;

public class GoToShop extends BaseShopCommand<CommandResult<Void, VoidVerifications>> {
    public GoToShop(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidVerifications> execute() {
        var result = driver.goToShop();
        return new CommandResult<>(result, context, VoidVerifications::new);
    }
}

