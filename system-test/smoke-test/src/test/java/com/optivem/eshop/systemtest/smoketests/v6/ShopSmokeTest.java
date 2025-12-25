package com.optivem.eshop.systemtest.smoketests.v6;

import com.optivem.eshop.systemtest.base.v5.BaseSystemTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;

public class ShopSmokeTest extends BaseSystemTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        app.shop().goToShop()
                .execute()
                .shouldSucceed();
    }
}
