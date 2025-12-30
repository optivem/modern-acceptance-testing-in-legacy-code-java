package com.optivem.eshop.systemtest.acceptancetests.v7.coupons;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;

public class PublishCouponPositiveTest extends BaseAcceptanceTest {
    @Disabled
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToPublishValidCoupon() {
        scenario
                .when().publishCoupon()
                .then().shouldSucceed();
    }
}
