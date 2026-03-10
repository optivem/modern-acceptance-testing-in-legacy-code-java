package com.optivem.eshop.systemtest.v4.base;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.dsl.core.app.AppConfiguration;
import com.optivem.eshop.systemtest.driver.adapter.external.erp.ErpRealDriver;
import com.optivem.eshop.systemtest.channel.ChannelType;
import com.optivem.eshop.systemtest.driver.adapter.shop.api.ShopApiDriver;
import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.driver.adapter.shop.ui.ShopUiDriver;
import com.optivem.eshop.systemtest.driver.adapter.external.tax.TaxRealDriver;
import com.optivem.eshop.systemtest.infrastructure.playwright.BrowserLifecycleExtension;
import com.optivem.common.Closer;
import com.optivem.testing.contexts.ChannelContext;
import com.optivem.testing.extensions.ChannelExtension;
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

        shopDriver = createChannelShopDriver(configuration);
        erpDriver = new ErpRealDriver(configuration.getErpBaseUrl());
        taxDriver = new TaxRealDriver(configuration.getTaxBaseUrl());
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
        Closer.close(erpDriver);
        Closer.close(taxDriver);
    }

    private ShopDriver createChannelShopDriver(AppConfiguration configuration) {
        var channel = ChannelContext.get();

        if(channel == null) {
            return null;
        }

        if (ChannelType.UI.equals(channel)) {
            return new ShopUiDriver(configuration.getShopUiBaseUrl(), BrowserLifecycleExtension.getBrowser());
        } else if (ChannelType.API.equals(channel)) {
            return new ShopApiDriver(configuration.getShopApiBaseUrl());
        } else {
            throw new IllegalStateException("Unknown channel: " + channel);
        }
    }
}

