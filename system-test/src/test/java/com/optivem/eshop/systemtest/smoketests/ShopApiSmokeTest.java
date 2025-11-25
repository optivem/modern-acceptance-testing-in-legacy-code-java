package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;

public class ShopApiSmokeTest extends BaseShopSmokeTest {

    @Override
    protected ShopDriver createDriver() {
        return DriverFactory.createShopApiDriver();
    }
}
