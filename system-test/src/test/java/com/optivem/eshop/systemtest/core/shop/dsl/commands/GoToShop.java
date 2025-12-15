package com.optivem.eshop.systemtest.core.shop.dsl.commands;

import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.testing.dsl.UseCaseResult;
import com.optivem.testing.dsl.UseCaseVoidSuccessVerification;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.BaseShopCommand;

public class GoToShop extends BaseShopCommand<Void, UseCaseVoidSuccessVerification> {
    public GoToShop(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public UseCaseResult<Void, UseCaseVoidSuccessVerification> execute() {
        var result = driver.goToShop();
        return new UseCaseResult<>(result, context, UseCaseVoidSuccessVerification::new);
    }
}

