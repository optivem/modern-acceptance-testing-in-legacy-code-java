package com.optivem.eshop.systemtest.core.clients;

import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.eshop.systemtest.core.clients.external.erp.ErpApiClient;
import com.optivem.eshop.systemtest.core.clients.system.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.clients.system.ui.ShopUiClient;

public class ClientFactory {

    public static ShopApiClient createShopApiClient() {
        return new ShopApiClient(TestConfiguration.getBaseUrl());
    }

    public static ShopUiClient createShopUiClient() {
        return new ShopUiClient(TestConfiguration.getBaseUrl());
    }

    public static ErpApiClient createErpApiClient() {
        return new ErpApiClient(TestConfiguration.getErpBaseUrl());
    }
}
