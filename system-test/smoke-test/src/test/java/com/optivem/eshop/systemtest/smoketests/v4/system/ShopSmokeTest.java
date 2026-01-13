package com.optivem.eshop.systemtest.smoketests.v4.system;

import com.optivem.eshop.systemtest.base.v4.BaseChannelDriverTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.test.Channel;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.commons.util.ResultAssert.assertThatResult;

public class ShopSmokeTest extends BaseChannelDriverTest {
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        var result = shopDriver.goToShop();
        assertThatResult(result).isSuccess();
    }
}
