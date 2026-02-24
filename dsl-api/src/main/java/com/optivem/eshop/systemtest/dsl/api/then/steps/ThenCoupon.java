package com.optivem.eshop.systemtest.dsl.api.then.steps;

public interface ThenCoupon extends ThenStep {

    ThenCoupon hasDiscountRate(double discountRate);

    ThenCoupon isValidFrom(String validFrom);

    ThenCoupon isValidTo(String validTo);

    ThenCoupon hasUsageLimit(int usageLimit);

    ThenCoupon hasUsedCount(int expectedUsedCount);
}

