package com.optivem.eshop.systemtest.base.v2;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.dsl.core.system.SystemConfiguration;
import com.optivem.eshop.systemtest.driver.core.erp.client.ErpRealClient;
import com.optivem.eshop.systemtest.driver.core.shop.client.api.ShopApiClient;
import com.optivem.eshop.systemtest.driver.core.shop.client.ui.ShopUiClient;
import com.optivem.eshop.systemtest.driver.core.tax.client.TaxRealClient;
import com.optivem.eshop.systemtest.infrastructure.playwright.BrowserLifecycleExtension;
import com.optivem.commons.util.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

public class BaseClientTest extends BaseConfigurableTest {
    protected SystemConfiguration configuration;

    protected ShopUiClient shopUiClient;
    protected ShopApiClient shopApiClient;
    protected ErpRealClient erpClient;
    protected TaxRealClient taxClient;


    @BeforeEach
    protected void setUpConfiguration() {
        configuration = loadConfiguration();
    }

    protected void setUpShopUiClient() {
        shopUiClient = new ShopUiClient(configuration.getShopUiBaseUrl(), BrowserLifecycleExtension.getBrowser());
    }

    protected void setUpShopApiClient() {
        shopApiClient = new ShopApiClient(configuration.getShopApiBaseUrl());
    }

    protected void setUpExternalClients() {
        erpClient = new ErpRealClient(configuration.getErpBaseUrl());
        taxClient = new TaxRealClient(configuration.getTaxBaseUrl());
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopUiClient);
        Closer.close(shopApiClient);
        Closer.close(erpClient);
        Closer.close(taxClient);
    }

    protected String createUniqueSku(String baseSku) {
        var suffix = UUID.randomUUID().toString().substring(0, 8);
        return baseSku + "-" + suffix;
    }
}

