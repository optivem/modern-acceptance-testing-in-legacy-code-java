package com.optivem.eshop.systemtest.dsl.api;

public interface WhenPublishCouponPort {
    WhenPublishCouponPort withCouponCode(String couponCode);

    WhenPublishCouponPort withDiscountRate(String discountRate);

    WhenPublishCouponPort withDiscountRate(double discountRate);

    WhenPublishCouponPort withValidFrom(String validFrom);

    WhenPublishCouponPort withValidTo(String validTo);

    WhenPublishCouponPort withUsageLimit(String usageLimit);

    WhenPublishCouponPort withUsageLimit(int usageLimit);

    ThenPort then();
}
