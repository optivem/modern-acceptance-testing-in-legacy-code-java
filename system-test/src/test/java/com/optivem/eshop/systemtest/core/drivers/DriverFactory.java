package com.optivem.eshop.systemtest.core.drivers;

import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.eshop.systemtest.core.drivers.external.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopUiDriver;

public class DriverFactory {

    public static ShopUiDriver createShopUiDriver() {
        return new ShopUiDriver(TestConfiguration.getShopUiBaseUrl());
    }

    public static ShopApiDriver createShopApiDriver() {
        return new ShopApiDriver(TestConfiguration.getShopApiBaseUrl());
    }

    public static ErpApiDriver createErpApiDriver() {
        return new ErpApiDriver(TestConfiguration.getErpApiBaseUrl());
    }

    public static TaxApiDriver createTaxApiDriver() {
        return new TaxApiDriver(TestConfiguration.getTaxApiBaseUrl());
    }
}

