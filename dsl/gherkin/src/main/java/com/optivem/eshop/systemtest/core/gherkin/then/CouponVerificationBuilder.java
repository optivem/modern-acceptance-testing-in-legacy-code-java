package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.verifications.BrowseCouponsVerification;

public class CouponVerificationBuilder {
    private final BrowseCouponsVerification verification;
    private String couponCode;

    public CouponVerificationBuilder(SystemDsl app) {
        // Execute browse coupons and get verification object
        this.verification = app.shop().browseCoupons()
                .execute()
                .shouldSucceed();
    }

    public CouponVerificationBuilder hasCouponCode(String couponCode) {
        this.couponCode = couponCode;
        verification.hasCouponWithCode(couponCode);
        return this;
    }

    public CouponVerificationBuilder hasDiscountRate(double discountRate) {
        verification.hasCouponWithCode(couponCode);
        verification.couponHasDiscountRate(couponCode, discountRate);
        return this;
    }

    public CouponVerificationBuilder isValidFrom(String validFrom) {
        verification.hasCouponWithCode(couponCode);
        verification.couponHasValidFrom(couponCode, validFrom);
        return this;
    }

    public CouponVerificationBuilder hasUsageLimit(int usageLimit) {
        verification.hasCouponWithCode(couponCode);
        verification.couponHasUsageLimit(couponCode, usageLimit);
        return this;
    }
}

