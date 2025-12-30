package com.optivem.eshop.systemtest.core.shop.dsl.verifications;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.CouponDto;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

import java.time.Instant;

public class BrowseCouponsVerification extends ResponseVerification<BrowseCouponsResponse, UseCaseContext> {

    public BrowseCouponsVerification(BrowseCouponsResponse response, UseCaseContext context) {
        super(response, context);
    }

    public BrowseCouponsVerification hasCouponWithCode(String couponCodeAlias) {
        var coupon = findCouponByCode(couponCodeAlias);
        if (coupon == null) {
            throw new AssertionError("Expected to find coupon with code '" + couponCodeAlias + "' but it was not found");
        }
        return this;
    }

    public BrowseCouponsVerification couponHasDiscountRate(String couponCodeAlias, double expectedDiscountRate) {
        var coupon = findCouponByCode(couponCodeAlias);
        if (coupon == null) {
            throw new AssertionError("Coupon with code '" + couponCodeAlias + "' not found");
        }

        double actualDiscountRate = coupon.getDiscountRate();
        if (Math.abs(actualDiscountRate - expectedDiscountRate) > 0.0001) {
            throw new AssertionError(String.format(
                    "Expected coupon '%s' to have discount rate %.2f, but was %.2f",
                    couponCodeAlias, expectedDiscountRate, actualDiscountRate));
        }
        return this;
    }

    public BrowseCouponsVerification couponHasValidFrom(String couponCodeAlias, String expectedValidFrom) {
        var coupon = findCouponByCode(couponCodeAlias);
        if (coupon == null) {
            throw new AssertionError("Coupon with code '" + couponCodeAlias + "' not found");
        }

        Instant actualValidFrom = coupon.getValidFrom();
        String actualValidFromString = actualValidFrom != null ? actualValidFrom.toString() : null;
        if (!expectedValidFrom.equals(actualValidFromString)) {
            throw new AssertionError(String.format(
                    "Expected coupon '%s' to have validFrom '%s', but was '%s'",
                    couponCodeAlias, expectedValidFrom, actualValidFromString));
        }
        return this;
    }

    public BrowseCouponsVerification couponHasUsageLimit(String couponCodeAlias, int expectedUsageLimit) {
        var coupon = findCouponByCode(couponCodeAlias);
        if (coupon == null) {
            throw new AssertionError("Coupon with code '" + couponCodeAlias + "' not found");
        }

        Integer actualUsageLimit = coupon.getUsageLimit();
        if (actualUsageLimit == null || actualUsageLimit != expectedUsageLimit) {
            throw new AssertionError(String.format(
                    "Expected coupon '%s' to have usage limit %d, but was %d",
                    couponCodeAlias, expectedUsageLimit, actualUsageLimit));
        }
        return this;
    }

    private CouponDto findCouponByCode(String couponCodeAlias) {
        if (response == null || response.getCoupons() == null) {
            return null;
        }

        var couponCode = context.getParamValue(couponCodeAlias);

        return response.getCoupons().stream()
                .filter(c -> couponCode.equals(c.getCode()))
                .findFirst()
                .orElse(null);
    }
}
