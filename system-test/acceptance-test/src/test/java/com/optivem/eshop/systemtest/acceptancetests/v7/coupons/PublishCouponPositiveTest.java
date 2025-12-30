package com.optivem.eshop.systemtest.acceptancetests.v7.coupons;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;

public class PublishCouponPositiveTest extends BaseAcceptanceTest {
    @TestTemplate
    @Channel(ChannelType.API)
    void shouldBeAbleToPublishValidCoupon() {
        scenario
                .when().publishCoupon()
                    .withCouponCode("SUMMER2025")
                    .withDiscountRate(0.15)
                .then().shouldSucceed();
    }
}
