package com.optivem.eshop.systemtest.acceptancetests.v7.coupons;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.test.Time;
import com.optivem.test.Channel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.params.provider.ValueSource;

public class PublishCouponNegativeTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ValueSource(strings = {"0.0", "-0.01", "-0.15"})
    void cannotPublishCouponWithZeroOrNegativeDiscount(String discountRate) {
        scenario
                .when().publishCoupon()
                    .withCouponCode("INVALID-COUPON")
                    .withDiscountRate(discountRate)
                .then().shouldFail()
                .errorMessage("The request contains one or more validation errors")
                .fieldErrorMessage("discountRate", "Discount rate must be greater than 0.00");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ValueSource(strings = {"1.01", "2.00"})
    void cannotPublishCouponWithDiscountGreaterThan100percent(String discountRate) {
        scenario
                .when().publishCoupon()
                .withCouponCode("INVALID-COUPON")
                .withDiscountRate(discountRate)
                .then().shouldFail()
                .errorMessage("The request contains one or more validation errors")
                .fieldErrorMessage("discountRate", "Discount rate must be at most 1.00");
    }
    
    @Time
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ValueSource(strings = {"2023-12-31T23:59:59Z", "2024-01-01T00:00:00Z", "2025-06-01T12:00:00Z"})
    void cannotPublishCouponWithValidToInThePast(String validTo) {
        scenario
                .given().clock().withTime("2026-01-01T12:00:00Z")
                .when().publishCoupon()
                    .withCouponCode("PAST-COUPON")
                    .withDiscountRate(0.15)
                    .withValidTo(validTo)
                .then().shouldFail()
                .errorMessage("The request contains one or more validation errors")
                .fieldErrorMessage("validTo", "Valid to date cannot be in the past");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotPublishCouponWithDuplicateCouponCode() {
        scenario
                .given().coupon().withCouponCode("EXISTING-COUPON").withDiscountRate(0.10)
                .when().publishCoupon()
                    .withCouponCode("EXISTING-COUPON")
                    .withDiscountRate(0.20)
                .then().shouldFail()
                .errorMessage("The request contains one or more validation errors")
                .fieldErrorMessage("couponCode", "Coupon code EXISTING-COUPON already exists");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ValueSource(strings = {"0", "-1", "-100"})
    void cannotPublishCouponWithZeroOrNegativeUsageLimit(String usageLimit) {
        scenario
                .when().publishCoupon()
                    .withCouponCode("INVALID-LIMIT")
                    .withDiscountRate(0.15)
                    .withUsageLimit(usageLimit)
                .then().shouldFail()
                .errorMessage("The request contains one or more validation errors")
                .fieldErrorMessage("usageLimit", "Usage limit must be positive");
    }

}
