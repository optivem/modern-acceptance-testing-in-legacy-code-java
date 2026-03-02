package com.optivem.eshop.systemtest.dsl.core.system.shop.usecases;

import com.optivem.eshop.systemtest.driver.port.shop.dtos.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.dsl.core.system.shop.usecases.base.BaseShopCommand;
import com.optivem.eshop.systemtest.dsl.core.system.shop.usecases.base.ShopUseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;

public class BrowseCoupons extends BaseShopCommand<BrowseCouponsResponse, BrowseCouponsVerification> {
    public BrowseCoupons(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public ShopUseCaseResult<BrowseCouponsResponse, BrowseCouponsVerification> execute() {
        var result = driver.browseCoupons();
        return new ShopUseCaseResult<>(result, context, BrowseCouponsVerification::new);
    }
}

