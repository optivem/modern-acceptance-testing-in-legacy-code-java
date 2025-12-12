package com.optivem.eshop.systemtest.core.shop.commands;

import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.VoidVerification;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.core.shop.commands.base.BaseShopCommand;

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

