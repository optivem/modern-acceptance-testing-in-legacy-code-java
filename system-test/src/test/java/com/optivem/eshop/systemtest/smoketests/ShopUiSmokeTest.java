package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopUiDriver;

public class ShopUiSmokeTest extends BaseShopSmokeTest {

    @Override
    protected ShopUiDriver createDriver() {
        return DriverFactory.createShopUiDriver();
    }
}
