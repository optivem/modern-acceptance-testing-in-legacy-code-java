package com.optivem.eshop.systemtest.dsl.core.app.shop.usecases;

import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.dsl.core.app.shop.usecases.base.BaseShopUseCase;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.app.VoidVerification;

public class GoToShop extends BaseShopUseCase<Void, VoidVerification> {
    public GoToShop(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public UseCaseResult<Void, VoidVerification> execute() {
        var result = driver.goToShop();
        return new UseCaseResult<>(result, context, VoidVerification::new);
    }
}

