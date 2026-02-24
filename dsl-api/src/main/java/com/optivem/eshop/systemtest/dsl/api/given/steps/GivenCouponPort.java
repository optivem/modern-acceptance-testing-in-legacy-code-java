package com.optivem.eshop.systemtest.dsl.api.given.steps;

public interface GivenCouponPort extends GivenStepPort {
    GivenCouponPort withCouponCode(String couponCode);

    GivenCouponPort withDiscountRate(String discountRate);

    GivenCouponPort withDiscountRate(double discountRate);

    GivenCouponPort withValidFrom(String validFrom);

    GivenCouponPort withValidTo(String validTo);

    GivenCouponPort withUsageLimit(String usageLimit);

    GivenCouponPort withUsageLimit(int usageLimit);

}
