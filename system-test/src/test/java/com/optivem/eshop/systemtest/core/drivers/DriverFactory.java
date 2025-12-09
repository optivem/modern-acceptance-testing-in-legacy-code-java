package com.optivem.eshop.systemtest.core.drivers;

import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.testing.channels.ChannelContext;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.shop.api.ShopApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ui.ShopUiDriver;

public class DriverFactory {

    public static ShopDriver createShopDriver() {
        String channel = ChannelContext.get();
        if ("UI".equals(channel)) {
            return new ShopUiDriver(TestConfiguration.getShopUiBaseUrl());
        } else if ("API".equals(channel)) {
            return new ShopApiDriver(TestConfiguration.getShopApiBaseUrl());
        } else {
            throw new IllegalStateException("Unknown channel: " + channel);
        }
    }

    public static ErpApiDriver createErpApiDriver() {
        return new ErpApiDriver(TestConfiguration.getErpApiBaseUrl());
    }

    public static TaxApiDriver createTaxApiDriver() {
        return new TaxApiDriver(TestConfiguration.getTaxApiBaseUrl());
    }
}

