package com.optivem.eshop.systemtest.core.shop.dsl.commands;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.PublishCouponRequest;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;
import com.optivem.test.dsl.UseCaseContext;
import com.optivem.test.dsl.VoidVerification;


public class PublishCoupon extends BaseShopCommand<Void, VoidVerification<UseCaseContext>> {
    private String couponCodeParamAlias;
    private String discountRate;
    private String validFrom;
    private String validTo;
    private String usageLimit;

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

    public PublishCoupon validFrom(String validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public PublishCoupon validTo(String validTo) {
        this.validTo = validTo;
        return this;
    }

    public PublishCoupon usageLimit(String usageLimit) {
        this.usageLimit = usageLimit;
        return this;
    }

    @Override
    public ShopUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {
        var couponCode = context.getParamValue(couponCodeParamAlias);
        
        // Dates are already in ISO 8601 format (YYYY-MM-DDTHH:mm:ssZ) from test
        // Pass them directly to the request
        var request = PublishCouponRequest.builder()
                .code(couponCode)
                .discountRate(discountRate)
                .validFrom(validFrom)
                .validTo(validTo)
                .usageLimit(usageLimit)
                .build();

        var result = driver.coupons().publishCoupon(request);
        return new ShopUseCaseResult<>(result, context, VoidVerification::new);
    }
}

