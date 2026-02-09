package com.optivem.eshop.systemtest.smoketests.v2.system;

import com.optivem.eshop.systemtest.base.v2.BaseClientTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.commons.util.ResultAssert.assertThatResult;

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

