package com.optivem.eshop.systemtest.smoketests.v7.system;

import com.optivem.eshop.systemtest.base.v5.BaseSystemDslTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.test.Channel;
import org.junit.jupiter.api.TestTemplate;

public class ShopSmokeTest extends BaseSystemDslTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        app.shop().goToShop()
                .execute()
                .shouldSucceed();
    }
}
