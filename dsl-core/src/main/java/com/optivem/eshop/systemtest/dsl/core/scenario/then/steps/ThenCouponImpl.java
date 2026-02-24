package com.optivem.eshop.systemtest.dsl.core.scenario.then.steps;

import com.optivem.eshop.systemtest.dsl.core.system.shared.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultContext;
import com.optivem.eshop.systemtest.dsl.api.then.steps.ThenCouponPort;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.BrowseCouponsVerification;

public class ThenCouponImpl<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> extends BaseThenStep<TSuccessResponse, TSuccessVerification> implements ThenCouponPort {
    private final BrowseCouponsVerification verification;
    private final String couponCode;

    public ThenCouponImpl(SystemDsl app, ExecutionResultContext executionResult, String couponCode, TSuccessVerification successVerification) {
        super(app, executionResult, successVerification);
        this.couponCode = couponCode;
        this.verification = app.shop().browseCoupons()
                .execute()
                .shouldSucceed();

        verification.hasCouponWithCode(couponCode);
    }

    public ThenCouponImpl<TSuccessResponse, TSuccessVerification> hasDiscountRate(double discountRate) {
        verification.couponHasDiscountRate(couponCode, discountRate);
        return this;
    }

    public ThenCouponImpl<TSuccessResponse, TSuccessVerification> isValidFrom(String validFrom) {
        verification.couponHasValidFrom(couponCode, validFrom);
        return this;
    }

    public ThenCouponImpl<TSuccessResponse, TSuccessVerification> isValidTo(String validTo) {
        verification.couponHasValidTo(couponCode, validTo);
        return this;
    }

    public ThenCouponImpl<TSuccessResponse, TSuccessVerification> hasUsageLimit(int usageLimit) {
        verification.couponHasUsageLimit(couponCode, usageLimit);
        return this;
    }

    public ThenCouponImpl<TSuccessResponse, TSuccessVerification> hasUsedCount(int expectedUsedCount) {
        verification.couponHasUsedCount(couponCode, expectedUsedCount);
        return this;
    }

    @Override
    public ThenCouponImpl<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}
