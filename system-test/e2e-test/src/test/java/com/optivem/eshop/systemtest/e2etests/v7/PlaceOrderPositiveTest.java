package com.optivem.eshop.systemtest.e2etests.v7;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.e2etests.v7.base.BaseE2eTest;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;

public class PlaceOrderPositiveTest extends BaseE2eTest {
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldCalculateSubtotalPrice() {
        scenario
                .given().product().withSku("ABC").withUnitPrice(20.00)
                .when().placeOrder().withOrderNumber("ORDER-1001").withSku("ABC").withQuantity(5)
                .then().shouldSucceed()
                .and().order("ORDER-1001").shouldHaveSubtotalPrice(100.00);
    }
}

