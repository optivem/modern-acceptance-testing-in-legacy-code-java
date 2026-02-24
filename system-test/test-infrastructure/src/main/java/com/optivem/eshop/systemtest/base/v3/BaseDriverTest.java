package com.optivem.eshop.systemtest.base.v3;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.dsl.core.system.SystemConfiguration;
import com.optivem.eshop.systemtest.driver.core.erp.driver.ErpRealDriver;
import com.optivem.eshop.systemtest.driver.core.shop.driver.api.ShopApiDriver;
import com.optivem.eshop.systemtest.driver.api.shop.ShopDriver;
import com.optivem.eshop.systemtest.driver.core.shop.driver.ui.ShopUiDriver;
import com.optivem.eshop.systemtest.driver.core.tax.driver.TaxRealDriver;
import com.optivem.eshop.systemtest.infrastructure.playwright.BrowserLifecycleExtension;
import com.optivem.commons.util.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseDriverTest extends BaseConfigurableTest {
    protected SystemConfiguration configuration;

    protected ShopDriver shopDriver;
    protected ErpRealDriver erpDriver;
    protected TaxRealDriver taxDriver;


    @BeforeEach
    protected void setUpConfiguration() {
        configuration = loadConfiguration();
    }

    protected void setUpShopUiDriver() {
        shopDriver = new ShopUiDriver(configuration.getShopUiBaseUrl(), BrowserLifecycleExtension.getBrowser());
    }

    protected void setUpShopApiDriver() {
        shopDriver = new ShopApiDriver(configuration.getShopApiBaseUrl());
    }

    protected void setUpExternalDrivers() {
        erpDriver = new ErpRealDriver(configuration.getErpBaseUrl());
        taxDriver = new TaxRealDriver(configuration.getTaxBaseUrl());
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
        Closer.close(erpDriver);
        Closer.close(taxDriver);
    }
}

