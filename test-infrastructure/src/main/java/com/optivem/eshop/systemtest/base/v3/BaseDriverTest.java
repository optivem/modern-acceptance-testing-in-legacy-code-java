package com.optivem.eshop.systemtest.base.v3;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemConfiguration;
import com.optivem.eshop.systemtest.core.erp.driver.ErpRealDriver;
import com.optivem.eshop.systemtest.core.shop.driver.api.ShopApiDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ui.ShopUiDriver;
import com.optivem.eshop.systemtest.core.tax.driver.TaxRealDriver;
import com.optivem.commons.util.Closer;
import org.junit.jupiter.api.AfterEach;

public class BaseDriverTest extends BaseConfigurableTest {
    protected ErpRealDriver erpDriver;
    protected TaxRealDriver taxDriver;
    protected ShopDriver shopDriver;
    protected SystemConfiguration configuration;

    protected void setUpExternalDrivers() {
        configuration = loadConfiguration();
        erpDriver = new ErpRealDriver(configuration.getErpBaseUrl());
        taxDriver = new TaxRealDriver(configuration.getTaxBaseUrl());
    }

    protected void setUpShopApiDriver() {
        if (configuration == null) {
            configuration = loadConfiguration();
        }
        shopDriver = new ShopApiDriver(configuration.getShopApiBaseUrl());
    }

    protected void setUpShopUiDriver() {
        if (configuration == null) {
            configuration = loadConfiguration();
        }
        shopDriver = new ShopUiDriver(configuration.getShopUiBaseUrl());
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
        Closer.close(erpDriver);
        Closer.close(taxDriver);
    }
}
