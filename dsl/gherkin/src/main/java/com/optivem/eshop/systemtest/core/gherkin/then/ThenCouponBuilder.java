package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.shop.dsl.coupons.verifications.BrowseCouponsVerification;

public class ThenCouponBuilder extends BaseThenVerificationBuilder {
    private final BrowseCouponsVerification verification;
    private final String couponCode;

    public ThenCouponBuilder(ThenClauseContext thenClauseContext, String couponCode) {
        super(thenClauseContext);
        this.couponCode = couponCode;
        this.verification = thenClauseContext.getApp().shop().browseCoupons()
                .execute()
                .shouldSucceed();

        verification.hasCouponWithCode(couponCode);
    }

    public ThenCouponBuilder hasDiscountRate(double discountRate) {
        verification.couponHasDiscountRate(couponCode, discountRate);
        return this;
    }

    public ThenCouponBuilder isValidFrom(String validFrom) {
        verification.couponHasValidFrom(couponCode, validFrom);
        return this;
    }

    public ThenCouponBuilder isValidTo(String validTo) {
        verification.couponHasValidTo(couponCode, validTo);
        return this;
    }

    public ThenCouponBuilder hasUsageLimit(int usageLimit) {
        verification.couponHasUsageLimit(couponCode, usageLimit);
        return this;
    }

    public ThenCouponBuilder hasUsedCount(int expectedUsedCount) {
        verification.couponHasUsedCount(couponCode, expectedUsedCount);
        return this;
    }
}
