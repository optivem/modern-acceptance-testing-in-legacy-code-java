package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;

class UiE2eTest extends BaseE2eTest {

    @Override
    protected ShopDriver createDriver() {
        return DriverFactory.createShopUiDriver();
    }

    // TODO: Verifying the different responses, e.g. for the place order response

    //        assertTrue(newOrderPage.getOrderNumber().isPresent(), "Order number should be present after placing order");
//        assertTrue(newOrderPage.getOriginalPrice().isPresent(), "Original price should be present after placing order");
//        assertTrue(newOrderPage.getOriginalPrice().get().compareTo(BigDecimal.ZERO) > 0, "Original price should be positive after placing order");
//
}

