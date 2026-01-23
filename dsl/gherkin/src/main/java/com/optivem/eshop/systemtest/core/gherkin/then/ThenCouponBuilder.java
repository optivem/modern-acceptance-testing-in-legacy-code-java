package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.coupons.BrowseCouponsVerification;

public class ThenCouponBuilder extends BaseThenBuilder {
    private final BrowseCouponsVerification verification;
    private final String couponCode;

    public ThenCouponBuilder(ThenClause thenClause, SystemDsl app, String couponCode) {
        super(thenClause);
        this.couponCode = couponCode;
        this.verification = app.shop().browseCoupons()
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

    public ThenCouponBuilder hasUsageLimit(int usageLimit) {
        verification.couponHasUsageLimit(couponCode, usageLimit);
        return this;
    }

    public ThenCouponBuilder hasUsedCount(int expectedUsedCount) {
        verification.couponHasUsedCount(couponCode, expectedUsedCount);
        return this;
    }
}

