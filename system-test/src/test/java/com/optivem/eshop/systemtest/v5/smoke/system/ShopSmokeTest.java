package com.optivem.eshop.systemtest.v5.smoke.system;

import com.optivem.eshop.systemtest.v5.base.BaseAppDslTest;
import com.optivem.eshop.dsl.channel.ChannelType;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.TestTemplate;

class ShopSmokeTest extends BaseAppDslTest {
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        app.shop().goToShop()
                .execute()
                .shouldSucceed();
    }
}

