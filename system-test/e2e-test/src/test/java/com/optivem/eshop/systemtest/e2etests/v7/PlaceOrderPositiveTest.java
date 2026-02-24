package com.optivem.eshop.systemtest.e2etests.v7;

import com.optivem.eshop.systemtest.core.system.shop.ChannelType;
import com.optivem.eshop.systemtest.e2etests.v7.base.BaseE2eTest;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.TestTemplate;

class PlaceOrderPositiveTest extends BaseE2eTest {
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrder() {
        scenario
                .when().placeOrder()
                .then().shouldSucceed();
    }
}


