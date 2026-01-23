package com.optivem.eshop.systemtest.core.shop.dsl.coupons;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.common.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.common.ShopUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;

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
