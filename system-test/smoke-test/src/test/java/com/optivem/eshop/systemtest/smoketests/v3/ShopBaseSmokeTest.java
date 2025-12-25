package com.optivem.eshop.systemtest.smoketests.v3;

import com.optivem.eshop.systemtest.base.v3.BaseDriverTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public abstract class ShopBaseSmokeTest extends BaseDriverTest {
    @BeforeEach
    void setUp() {
        setShopApiDriver();
    }

    protected abstract void setShopApiDriver();

    @Test
    void shouldBeAbleToGoToShop() {
        var result = shopDriver.goToShop();
        assertThatResult(result).isSuccess();
    }
}
