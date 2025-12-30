package com.optivem.eshop.systemtest.core.shop.dsl.commands;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.PublishCouponRequest;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.VoidVerification;

public class PublishCoupon extends BaseShopCommand<Void, VoidVerification<UseCaseContext>> {
    private String couponCodeParamAlias;
    private String discountRate;

    public PublishCoupon(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public PublishCoupon couponCode(String couponCodeParamAlias) {
        this.couponCodeParamAlias = couponCodeParamAlias;
        return this;
    }

    public PublishCoupon discountRate(String discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    @Override
    public ShopUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {
        var couponCode = context.getParamValue(couponCodeParamAlias);
        
        var request = PublishCouponRequest.builder()
                .code(couponCode)
                .discountRate(discountRate)
                .build();

        var result = driver.coupons().publishCoupon(request);
        return new ShopUseCaseResult<>(result, context, VoidVerification::new);
    }
}

