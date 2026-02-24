package com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.shop.dtos.coupons.PublishCouponRequest;
import com.optivem.eshop.systemtest.driver.api.shop.ShopDriver;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.base.BaseShopCommand;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.base.ShopUseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.system.shared.VoidVerification;

public class PublishCoupon extends BaseShopCommand<Void, VoidVerification> {
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
    public ShopUseCaseResult<Void, VoidVerification> execute() {
        var couponCode = context.getParamValue(couponCodeParamAlias);

        var request = PublishCouponRequest.builder()
                .code(couponCode)
                .discountRate(discountRate)
                .validFrom(validFrom)
                .validTo(validTo)
                .usageLimit(usageLimit)
                .build();

        var result = driver.publishCoupon(request);
        return new ShopUseCaseResult<>(result, context, VoidVerification::new);
    }
}
