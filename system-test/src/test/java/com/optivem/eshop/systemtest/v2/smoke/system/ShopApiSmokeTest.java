package com.optivem.eshop.systemtest.v2.smoke.system;

import com.optivem.eshop.systemtest.v2.base.BaseClientTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.eshop.dsl.common.ResultAssert.assertThatResult;

class ShopApiSmokeTest extends BaseClientTest {
    @BeforeEach
    void setUp() {
        setUpShopApiClient();
    }

    @Test
    void shouldBeAbleToGoToShop() {
        var result = shopApiClient.health().checkHealth();
        assertThatResult(result).isSuccess();
    }
}


