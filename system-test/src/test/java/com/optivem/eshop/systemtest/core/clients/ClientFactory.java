package com.optivem.eshop.systemtest.core.clients;

import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.eshop.systemtest.core.clients.external.erp.ErpApiClient;
import com.optivem.eshop.systemtest.core.clients.external.tax.TaxApiClient;
import com.optivem.eshop.systemtest.core.clients.system.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.clients.system.ui.ShopUiClient;

public class ClientFactory {

    public static ShopUiClient createShopUiClient() {
        return new ShopUiClient(TestConfiguration.getShopUiBaseUrl());
    }

    public static ShopApiClient createShopApiClient() {
        return new ShopApiClient(TestConfiguration.getShopApiBaseUrl());
    }

    public static ErpApiClient createErpApiClient() {
        return new ErpApiClient(TestConfiguration.getErpApiBaseUrl());
    }

    public static TaxApiClient createTaxApiClient() {
        return new TaxApiClient(TestConfiguration.getTaxApiBaseUrl());
    }
}
