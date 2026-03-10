package com.optivem.eshop.systemtest.v7.acceptance;

import com.optivem.eshop.systemtest.v7.acceptance.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.channel.ChannelType;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.OrderStatus;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;

class SubmitReviewPositiveTest extends BaseAcceptanceTest {
    @Disabled("RED 3 - Driver")
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void canSubmitReviewOnDeliveredOrder() {
        scenario
                .given().order()
                    .withStatus(OrderStatus.DELIVERED)
                .and().product()
                    .isReviewable()
                .when().submitReview()
                    .withRating("5")
                    .withComment("Quality product")
                .then().shouldSucceed();
    }
}
