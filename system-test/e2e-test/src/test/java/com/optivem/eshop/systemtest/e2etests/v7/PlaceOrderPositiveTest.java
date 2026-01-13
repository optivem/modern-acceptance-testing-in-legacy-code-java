package com.optivem.eshop.systemtest.e2etests.v7;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.e2etests.v7.base.BaseE2eTest;
import com.optivem.test.Channel;
import org.junit.jupiter.api.TestTemplate;

public class PlaceOrderPositiveTest extends BaseE2eTest {
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrder() {
        scenario
                .when().placeOrder()
                .then().shouldSucceed();
    }
}


