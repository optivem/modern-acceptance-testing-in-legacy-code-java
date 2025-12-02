package com.optivem.eshop.systemtest.core.drivers;

import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.atdd.commons.channels.ChannelContext;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.shop.api.ShopApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ui.ShopUiDriver;

public class DriverFactory {

    public static ShopDriver createShopDriver() {
        String channel = ChannelContext.get();
        if ("UI".equals(channel)) {
            return createShopUiDriver();
        } else if ("API".equals(channel)) {
            return createShopApiDriver();
        } else {
            throw new IllegalStateException("Unknown channel: " + channel);
        }
    }

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

