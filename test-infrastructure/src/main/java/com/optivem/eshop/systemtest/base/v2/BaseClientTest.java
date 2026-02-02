package com.optivem.eshop.systemtest.base.v2;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemConfiguration;
import com.optivem.eshop.systemtest.core.erp.client.ErpRealClient;
import com.optivem.eshop.systemtest.core.shop.client.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.shop.client.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.tax.client.TaxRealClient;
import com.optivem.commons.util.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseClientTest extends BaseConfigurableTest {
    protected SystemConfiguration configuration;

    protected ErpRealClient erpClient;
    protected TaxRealClient taxClient;
    protected ShopApiClient shopApiClient;
    protected ShopUiClient shopUiClient;


    @BeforeEach
    protected void setUpConfiguration() {
        configuration = loadConfiguration();
    }

    protected void setUpExternalClients() {
        erpClient = new ErpRealClient(configuration.getErpBaseUrl());
        taxClient = new TaxRealClient(configuration.getTaxBaseUrl());
    }

    protected void setUpShopApiClient() {
        shopApiClient = new ShopApiClient(configuration.getShopApiBaseUrl());
    }

    protected void setUpShopUiClient() {
        shopUiClient = new ShopUiClient(configuration.getShopUiBaseUrl());
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopUiClient);
        Closer.close(shopApiClient);
        Closer.close(erpClient);
        Closer.close(taxClient);
    }
}

