package com.optivem.eshop.systemtest.acceptancetests.v7.coupons;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.test.Channel;
import com.optivem.test.Time;

import org.junit.jupiter.api.TestTemplate;

public class PublishCouponPositiveTest extends BaseAcceptanceTest {
    @Time
    @TestTemplate
    @Channel({ ChannelType.UI, ChannelType.API })
    void shouldBeAbleToPublishValidCoupon() {
        scenario
                .given().clock().withTime("2024-01-01T00:00:00Z")
                .when().publishCoupon()
                    .withCouponCode("SUMMER2025")
                    .withDiscountRate(0.15)
                    .withValidFrom("2024-06-01T00:00:00Z")
                    .withValidTo("2024-08-31T23:59:59Z")
                    .withUsageLimit(100)
                .then().shouldSucceed();
    }

    @TestTemplate
    @Channel({ ChannelType.UI, ChannelType.API })
    void shouldBeAbleToPublishCouponWithEmptyOptionalFields() {
        scenario
                .when().publishCoupon()
                .withCouponCode("SUMMER2025")
                .withDiscountRate(0.15)
                .withValidFrom("")
                .withValidTo("")
                .withUsageLimit("")
                .then().shouldSucceed();
    }

    @Time
    @TestTemplate
    @Channel({ ChannelType.UI, ChannelType.API })
    void shouldBeAbleToCorrectlySaveCoupon() {
        scenario
                .given().clock().withTime("2024-01-01T00:00:00Z")
                .when().publishCoupon()
                .withCouponCode("SUMMER2025")
                .withDiscountRate(0.15)
                .withValidFrom("2024-06-01T00:00:00Z")
                .withValidTo("2024-08-31T23:59:59Z")
                .withUsageLimit(100)
                .then().coupon("SUMMER2025")
                .hasDiscountRate(0.15)
                .isValidFrom("2024-06-01T00:00:00Z")
                .isValidTo("2024-08-31T23:59:59Z")
                .hasUsageLimit(100)
                .hasUsedCount(0);
    }
}
