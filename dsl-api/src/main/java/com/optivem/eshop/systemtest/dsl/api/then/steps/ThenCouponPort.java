package com.optivem.eshop.systemtest.dsl.api.then.steps;

public interface ThenCouponPort extends ThenStepPort {

    ThenCouponPort hasDiscountRate(double discountRate);

    ThenCouponPort isValidFrom(String validFrom);

    ThenCouponPort isValidTo(String validTo);

    ThenCouponPort hasUsageLimit(int usageLimit);

    ThenCouponPort hasUsedCount(int expectedUsedCount);
}
