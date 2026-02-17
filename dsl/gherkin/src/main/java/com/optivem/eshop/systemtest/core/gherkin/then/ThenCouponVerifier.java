package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.coupons.BrowseCouponsVerification;

public class ThenCouponVerifier<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> extends BaseThenVerifier<TSuccessResponse, TSuccessVerification> {
    private final BrowseCouponsVerification verification;
    private final String couponCode;

    public ThenCouponVerifier(SystemDsl app, ExecutionResultContext executionResult, String couponCode, TSuccessVerification successVerification) {
        super(app, executionResult, successVerification);
        this.couponCode = couponCode;
        this.verification = app.shop().browseCoupons()
                .execute()
                .shouldSucceed();

        verification.hasCouponWithCode(couponCode);
    }

    public ThenCouponVerifier<TSuccessResponse, TSuccessVerification> hasDiscountRate(double discountRate) {
        verification.couponHasDiscountRate(couponCode, discountRate);
        return this;
    }

    public ThenCouponVerifier<TSuccessResponse, TSuccessVerification> isValidFrom(String validFrom) {
        verification.couponHasValidFrom(couponCode, validFrom);
        return this;
    }

    public ThenCouponVerifier<TSuccessResponse, TSuccessVerification> isValidTo(String validTo) {
        verification.couponHasValidTo(couponCode, validTo);
        return this;
    }

    public ThenCouponVerifier<TSuccessResponse, TSuccessVerification> hasUsageLimit(int usageLimit) {
        verification.couponHasUsageLimit(couponCode, usageLimit);
        return this;
    }

    public ThenCouponVerifier<TSuccessResponse, TSuccessVerification> hasUsedCount(int expectedUsedCount) {
        verification.couponHasUsedCount(couponCode, expectedUsedCount);
        return this;
    }
}
