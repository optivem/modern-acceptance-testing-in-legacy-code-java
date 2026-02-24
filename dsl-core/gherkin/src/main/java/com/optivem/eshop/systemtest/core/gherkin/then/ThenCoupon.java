package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.dsl.api.then.ThenCouponPort;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.BrowseCouponsVerification;

public class ThenCoupon<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> extends BaseThenStep<TSuccessResponse, TSuccessVerification> implements ThenCouponPort {
    private final BrowseCouponsVerification verification;
    private final String couponCode;

    public ThenCoupon(SystemDsl app, ExecutionResultContext executionResult, String couponCode, TSuccessVerification successVerification) {
        super(app, executionResult, successVerification);
        this.couponCode = couponCode;
        this.verification = app.shop().browseCoupons()
                .execute()
                .shouldSucceed();

        verification.hasCouponWithCode(couponCode);
    }

    public ThenCoupon<TSuccessResponse, TSuccessVerification> hasDiscountRate(double discountRate) {
        verification.couponHasDiscountRate(couponCode, discountRate);
        return this;
    }

    public ThenCoupon<TSuccessResponse, TSuccessVerification> isValidFrom(String validFrom) {
        verification.couponHasValidFrom(couponCode, validFrom);
        return this;
    }

    public ThenCoupon<TSuccessResponse, TSuccessVerification> isValidTo(String validTo) {
        verification.couponHasValidTo(couponCode, validTo);
        return this;
    }

    public ThenCoupon<TSuccessResponse, TSuccessVerification> hasUsageLimit(int usageLimit) {
        verification.couponHasUsageLimit(couponCode, usageLimit);
        return this;
    }

    public ThenCoupon<TSuccessResponse, TSuccessVerification> hasUsedCount(int expectedUsedCount) {
        verification.couponHasUsedCount(couponCode, expectedUsedCount);
        return this;
    }

    @Override
    public ThenCoupon<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}
