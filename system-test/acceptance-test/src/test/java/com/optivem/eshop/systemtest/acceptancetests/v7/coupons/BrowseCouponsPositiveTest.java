package com.optivem.eshop.systemtest.acceptancetests.v7.coupons;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.dsl.core.system.shop.ChannelType;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.TestTemplate;

class BrowseCouponsPositiveTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ ChannelType.UI, ChannelType.API })
    void shouldBeAbleToBrowseCoupons() {
        scenario
                .when().browseCoupons()
                .then().shouldSucceed();
    }

}
