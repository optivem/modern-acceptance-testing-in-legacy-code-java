package com.optivem.eshop.systemtest.dsl.shop.commands;

import com.optivem.eshop.systemtest.dsl.shop.drivers.ShopDriver;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.VoidVerification;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.dsl.shop.commands.base.BaseShopCommand;

public class GoToShop extends BaseShopCommand<Void, VoidVerification> {
    public GoToShop(ShopDriver driver, Context context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var result = driver.goToShop();
        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

