package com.optivem.eshop.systemtest.smoketests.v3.system;

import com.optivem.eshop.systemtest.base.v3.BaseDriverTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.commons.util.ResultAssert.assertThatResult;

public abstract class ShopBaseSmokeTest extends BaseDriverTest {
    @BeforeEach
    void setUp() {
        setShopDriver();
    }

    protected abstract void setShopDriver();

    @Test
    void shouldBeAbleToGoToShop() {
        var result = shopDriver.goToShop();
        assertThatResult(result).isSuccess();
    }
}

