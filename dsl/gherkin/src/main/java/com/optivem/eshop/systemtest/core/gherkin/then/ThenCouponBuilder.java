package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.VoidVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.coupons.verifications.BrowseCouponsVerification;

public class ThenCouponBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenBuilder<TSuccessResponse, TSuccessVerification> {
    private final BrowseCouponsVerification verification;
    private final String couponCode;

    public ThenCouponBuilder(ThenClause<TSuccessResponse, TSuccessVerification> thenClause, SystemDsl app, String couponCode) {
        super(thenClause);
        this.couponCode = couponCode;
        this.verification = app.shop().browseCoupons()
                .execute()
                .shouldSucceed();

        verification.hasCouponWithCode(couponCode);
    }

    public ThenCouponBuilder<TSuccessResponse, TSuccessVerification> hasDiscountRate(double discountRate) {
        verification.couponHasDiscountRate(couponCode, discountRate);
        return this;
    }

    public ThenCouponBuilder<TSuccessResponse, TSuccessVerification> isValidFrom(String validFrom) {
        verification.couponHasValidFrom(couponCode, validFrom);
        return this;
    }

    public ThenCouponBuilder<TSuccessResponse, TSuccessVerification> isValidTo(String validTo) {
        verification.couponHasValidTo(couponCode, validTo);
        return this;
    }

    public ThenCouponBuilder<TSuccessResponse, TSuccessVerification> hasUsageLimit(int usageLimit) {
        verification.couponHasUsageLimit(couponCode, usageLimit);
        return this;
    }

    public ThenCouponBuilder<TSuccessResponse, TSuccessVerification> hasUsedCount(int expectedUsedCount) {
        verification.couponHasUsedCount(couponCode, expectedUsedCount);
        return this;
    }
}

