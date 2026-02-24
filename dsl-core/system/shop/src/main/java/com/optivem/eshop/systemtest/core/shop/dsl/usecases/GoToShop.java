package com.optivem.eshop.systemtest.core.shop.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.base.ShopUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.VoidVerification;

public class GoToShop extends BaseShopCommand<Void, VoidVerification> {
    public GoToShop(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public ShopUseCaseResult<Void, VoidVerification> execute() {
        var result = driver.goToShop();
        return new ShopUseCaseResult<>(result, context, VoidVerification::new);
    }
}
