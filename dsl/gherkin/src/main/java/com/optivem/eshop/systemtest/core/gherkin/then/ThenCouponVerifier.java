package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.shop.dsl.coupons.verifications.BrowseCouponsVerification;

public class ThenCouponVerifier extends BaseThenStateVerifier {
    private final BrowseCouponsVerification verification;
    private final String couponCode;

    public ThenCouponVerifier(SystemDsl app, ExecutionResultContext executionResult, String couponCode) {
        super(app, executionResult);
        this.couponCode = couponCode;
        this.verification = app.shop().browseCoupons()
                .execute()
                .shouldSucceed();

        verification.hasCouponWithCode(couponCode);
    }

    public ThenCouponVerifier hasDiscountRate(double discountRate) {
        verification.couponHasDiscountRate(couponCode, discountRate);
        return this;
    }

    public ThenCouponVerifier isValidFrom(String validFrom) {
        verification.couponHasValidFrom(couponCode, validFrom);
        return this;
    }

    public ThenCouponVerifier isValidTo(String validTo) {
        verification.couponHasValidTo(couponCode, validTo);
        return this;
    }

    public ThenCouponVerifier hasUsageLimit(int usageLimit) {
        verification.couponHasUsageLimit(couponCode, usageLimit);
        return this;
    }

    public ThenCouponVerifier hasUsedCount(int expectedUsedCount) {
        verification.couponHasUsedCount(couponCode, expectedUsedCount);
        return this;
    }
}
