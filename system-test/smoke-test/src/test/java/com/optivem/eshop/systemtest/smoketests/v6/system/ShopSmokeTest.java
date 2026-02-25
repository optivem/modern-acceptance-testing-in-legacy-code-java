package com.optivem.eshop.systemtest.smoketests.v6.system;

import com.optivem.eshop.systemtest.base.v6.BaseScenarioDslTest;
import com.optivem.eshop.systemtest.dsl.core.system.shop.ChannelType;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.TestTemplate;

class ShopSmokeTest extends BaseScenarioDslTest {
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        scenario
                .when().goToShop()
                .then().shouldSucceed();
    }
}

