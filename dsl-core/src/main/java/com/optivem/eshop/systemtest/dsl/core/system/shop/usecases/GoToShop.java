package com.optivem.eshop.systemtest.dsl.core.system.shop.usecases;

import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.dsl.core.system.shop.usecases.base.BaseShopCommand;
import com.optivem.eshop.systemtest.dsl.core.system.shop.usecases.base.ShopUseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.system.shared.VoidVerification;

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

