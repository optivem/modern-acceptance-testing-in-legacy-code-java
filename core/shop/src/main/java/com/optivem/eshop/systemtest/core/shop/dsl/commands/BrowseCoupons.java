package com.optivem.eshop.systemtest.core.shop.dsl.commands;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;
import com.optivem.eshop.systemtest.core.shop.dsl.verifications.BrowseCouponsVerification;
import com.optivem.test.dsl.UseCaseContext;

public class BrowseCoupons extends BaseShopCommand<BrowseCouponsResponse, BrowseCouponsVerification> {

    public BrowseCoupons(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public ShopUseCaseResult<BrowseCouponsResponse, BrowseCouponsVerification> execute() {
        var request = new BrowseCouponsRequest();
        var result = driver.coupons().browseCoupons(request);
        return new ShopUseCaseResult<>(result, context, BrowseCouponsVerification::new);
    }
}
