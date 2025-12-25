package com.optivem.eshop.systemtest.base.v4;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.configuration.Environment;
import com.optivem.eshop.systemtest.configuration.PropertyLoader;
import com.optivem.eshop.systemtest.configuration.SystemConfigurationLoader;
import com.optivem.eshop.systemtest.core.SystemConfiguration;
import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.driver.ErpRealDriver;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.driver.ShopApiDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ShopUiDriver;
import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.driver.TaxRealDriver;
import com.optivem.lang.Closer;
import com.optivem.testing.channels.ChannelContext;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.testing.dsl.ExternalSystemMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class BaseChannelDriverTest extends BaseConfigurableTest {
    protected ShopDriver shopDriver;
    protected ErpRealDriver erpDriver;
    protected TaxRealDriver taxDriver;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        shopDriver = createShopDriver(configuration);
        erpDriver = new ErpRealDriver(configuration.getErpBaseUrl());
        taxDriver = new TaxRealDriver(configuration.getTaxBaseUrl());
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
        Closer.close(erpDriver);
        Closer.close(taxDriver);
    }

    private ShopDriver createShopDriver(SystemConfiguration configuration) {
        var channel = ChannelContext.get();
        if (ChannelType.UI.equals(channel)) {
            return new ShopUiDriver(configuration.getShopUiBaseUrl());
        } else if (ChannelType.API.equals(channel)) {
            return new ShopApiDriver(configuration.getShopApiBaseUrl());
        } else {
            throw new IllegalStateException("Unknown channel: " + channel);
        }
    }
}