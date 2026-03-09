package com.optivem.eshop.systemtest.v7.acceptance;

import com.optivem.eshop.systemtest.v7.acceptance.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.channel.ChannelType;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.OrderStatus;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;

@Disabled("In Progress - Implementation")
class SubmitReviewPositiveTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void canSubmitReviewOnDeliveredOrder() {
        scenario
                .given().order()
                    .withStatus(OrderStatus.DELIVERED)
                .when().submitReview()
                    .withRating("5")
                    .withComment("Quality product")
                .then().shouldSucceed()
                .and().review()
                    .hasRating(5)
                    .hasComment("Quality product")
                    .hasTimestamp();
    }
}
