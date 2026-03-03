package com.optivem.eshop.systemtest.smoketests.v7.external;

import com.optivem.eshop.systemtest.base.v7.BaseShopScenarioDslTest;
import org.junit.jupiter.api.Test;

class ErpSmokeTest extends BaseShopScenarioDslTest {
    @Test
    void shouldBeAbleToGoToErp() {
        scenario
                .when().goToErp()
                .then().shouldSucceed();
    }
}


