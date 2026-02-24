package com.optivem.eshop.systemtest.dsl.api.when.steps;

import com.optivem.eshop.systemtest.dsl.api.then.Then;

public interface WhenPublishCoupon {
    WhenPublishCoupon withCouponCode(String couponCode);

    WhenPublishCoupon withDiscountRate(String discountRate);

    WhenPublishCoupon withDiscountRate(double discountRate);

    WhenPublishCoupon withValidFrom(String validFrom);

    WhenPublishCoupon withValidTo(String validTo);

    WhenPublishCoupon withUsageLimit(String usageLimit);

    WhenPublishCoupon withUsageLimit(int usageLimit);

    Then then();
}

