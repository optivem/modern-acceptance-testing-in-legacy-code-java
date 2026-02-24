package com.optivem.eshop.systemtest.smoketests.v5.system;

import com.optivem.eshop.systemtest.base.v5.BaseSystemDslTest;
import com.optivem.eshop.systemtest.core.system.shop.ChannelType;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.TestTemplate;

class ShopSmokeTest extends BaseSystemDslTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        app.shop().goToShop()
                .execute()
                .shouldSucceed();
    }
}
