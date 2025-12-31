package com.optivem.eshop.systemtest.acceptancetests.v7.orders;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.testing.annotations.Time;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;


public class CancelOrderPositiveTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToCancelAnyPlacedOrder() {
        scenario
                .given().order().withStatus(OrderStatus.PLACED)
                .when().cancelOrder()
                .then().shouldSucceed();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldHaveCancelledStatusWhenCancelled() {
        scenario
                .given().order()
                .when().cancelOrder()
                .then().order().hasStatus(OrderStatus.CANCELLED);
    }

    @Time
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToCancelAnOrderOn31stDecBetween2200And2230() {
        scenario
                .given().order().withStatus(OrderStatus.PLACED)
                .and().clock().withTime("2024-12-31T22:15:00Z")
                .when().cancelOrder()
                .then().shouldFail().errorMessage("Order cancellation is not allowed on December 31st between 22:00 and 23:00")
                .and().order().hasStatus(OrderStatus.PLACED);
    }


}

