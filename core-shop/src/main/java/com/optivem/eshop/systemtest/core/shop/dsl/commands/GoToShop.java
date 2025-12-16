package com.optivem.eshop.systemtest.core.shop.dsl.commands;

import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.testing.dsl.VoidResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;

public class GoToShop extends BaseShopCommand<Void, VoidResponseVerification<UseCaseContext>> {
    public GoToShop(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public ShopUseCaseResult<Void, VoidResponseVerification<UseCaseContext>> execute() {
        var result = driver.goToShop();
        return new ShopUseCaseResult<>(result, context, VoidResponseVerification::new);
    }
}

