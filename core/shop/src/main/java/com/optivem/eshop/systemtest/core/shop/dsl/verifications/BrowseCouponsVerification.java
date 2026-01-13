package com.optivem.eshop.systemtest.core.shop.dsl.verifications;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.CouponDto;
import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.UseCaseContext;

import java.time.Instant;

public class BrowseCouponsVerification extends ResponseVerification<BrowseCouponsResponse, UseCaseContext> {

    public BrowseCouponsVerification(BrowseCouponsResponse response, UseCaseContext context) {
        super(response, context);
    }

    public BrowseCouponsVerification hasCouponWithCode(String couponCodeAlias) {
        findCouponByCode(couponCodeAlias); // Throws if not found
        return this;
    }

    public BrowseCouponsVerification couponHasDiscountRate(String couponCodeAlias, double expectedDiscountRate) {
        var coupon = findCouponByCode(couponCodeAlias);

        double actualDiscountRate = coupon.getDiscountRate();
        if (actualDiscountRate != expectedDiscountRate) {
            throw new AssertionError(String.format(
                    "Expected coupon '%s' to have discount rate %.2f, but was %.2f",
                    couponCodeAlias, expectedDiscountRate, actualDiscountRate));
        }
        return this;
    }

    public BrowseCouponsVerification couponHasValidFrom(String couponCodeAlias, String expectedValidFrom) {
        var coupon = findCouponByCode(couponCodeAlias);

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

        var actualUsageLimit = coupon.getUsageLimit();
        if (actualUsageLimit == null || actualUsageLimit != expectedUsageLimit) {
            throw new AssertionError(String.format(
                    "Expected coupon '%s' to have usage limit %d, but was %d",
                    couponCodeAlias, expectedUsageLimit, actualUsageLimit));
        }
        return this;
    }

    public BrowseCouponsVerification couponHasUsedCount(String couponCode, int expectedUsedCount) {
        var coupon = findCouponByCode(couponCode);

        var actualUsedCount = coupon.getUsedCount();
        if (actualUsedCount != expectedUsedCount) {
            throw new AssertionError(String.format(
                    "Expected coupon '%s' to have used count %d, but was %d",
                    couponCode, expectedUsedCount, actualUsedCount));
        }
        return this;
    }

    private CouponDto findCouponByCode(String couponCodeAlias) {
        if (response == null || response.getCoupons() == null) {
            throw new AssertionError("No coupons found in response");
        }

        var couponCode = context.getParamValue(couponCodeAlias);

        return response.getCoupons().stream()
                .filter(c -> couponCode.equals(c.getCode()))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        String.format("Coupon with code '%s' not found. Available coupons: %s",
                                couponCode,
                                response.getCoupons().stream()
                                        .map(CouponDto::getCode)
                                        .toList())));
    }

}
