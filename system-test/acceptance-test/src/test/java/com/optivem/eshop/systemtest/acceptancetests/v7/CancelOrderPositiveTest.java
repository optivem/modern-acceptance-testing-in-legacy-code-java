package com.optivem.eshop.systemtest.acceptancetests.v7;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.enums.OrderStatus;
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
}

