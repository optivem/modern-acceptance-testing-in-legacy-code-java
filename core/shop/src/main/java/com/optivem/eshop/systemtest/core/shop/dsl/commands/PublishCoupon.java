package com.optivem.eshop.systemtest.core.shop.dsl.commands;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.PublishCouponRequest;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.VoidVerification;

public class PublishCoupon extends BaseShopCommand<Void, VoidVerification<UseCaseContext>> {
    private String couponCode;
    private String discountRate;

    public PublishCoupon(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public PublishCoupon couponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    public PublishCoupon discountRate(String discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    @Override
    public ShopUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {
        var request = new PublishCouponRequest();
        // TODO: Set request properties when PublishCouponRequest is implemented

        var result = driver.coupons().publishCoupon(request);
        return new ShopUseCaseResult<>(result, context, VoidVerification::new);
    }
}

