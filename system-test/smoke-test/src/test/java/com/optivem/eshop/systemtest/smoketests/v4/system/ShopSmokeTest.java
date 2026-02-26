package com.optivem.eshop.systemtest.smoketests.v4.system;

import com.optivem.eshop.systemtest.base.v4.BaseChannelDriverTest;
import com.optivem.eshop.systemtest.dsl.core.system.shop.ChannelType;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.common.util.ResultAssert.assertThatResult;

class ShopSmokeTest extends BaseChannelDriverTest {
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        var result = shopDriver.goToShop();
        assertThatResult(result).isSuccess();
    }
}

