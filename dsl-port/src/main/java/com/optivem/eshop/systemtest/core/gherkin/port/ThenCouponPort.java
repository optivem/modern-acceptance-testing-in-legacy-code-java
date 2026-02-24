package com.optivem.eshop.systemtest.core.gherkin.port;

public interface ThenCouponPort {
    ThenCouponPort and();

    ThenCouponPort hasDiscountRate(double discountRate);

    ThenCouponPort isValidFrom(String validFrom);

    ThenCouponPort isValidTo(String validTo);

    ThenCouponPort hasUsageLimit(int usageLimit);

    ThenCouponPort hasUsedCount(int expectedUsedCount);
}
