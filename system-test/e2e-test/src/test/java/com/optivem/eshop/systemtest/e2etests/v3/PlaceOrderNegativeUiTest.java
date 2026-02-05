package com.optivem.eshop.systemtest.e2etests.v3;

import org.junit.jupiter.api.Disabled;

@Disabled("V3 tests disabled for now")
class PlaceOrderNegativeUiTest extends PlaceOrderNegativeBaseTest {

    @Override
    protected void setShopDriver() {
        setUpShopUiDriver();
    }
}
