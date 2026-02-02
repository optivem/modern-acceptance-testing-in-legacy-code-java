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
        shopDriver = new ShopUiDriver(configuration.getShopUiBaseUrl());
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
