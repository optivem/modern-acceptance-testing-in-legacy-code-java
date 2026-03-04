package com.optivem.eshop.systemtest.configuration;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.app.AppConfiguration;
import com.optivem.eshop.systemtest.driver.port.clock.ClockDriver;
import com.optivem.eshop.systemtest.driver.port.erp.ErpDriver;
import com.optivem.eshop.systemtest.channel.ChannelType;
import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.driver.port.tax.TaxDriver;
import com.optivem.eshop.systemtest.driver.adapter.clock.ClockRealDriver;
import com.optivem.eshop.systemtest.driver.adapter.clock.ClockStubDriver;
import com.optivem.eshop.systemtest.driver.adapter.erp.ErpRealDriver;
import com.optivem.eshop.systemtest.driver.adapter.erp.ErpStubDriver;
import com.optivem.eshop.systemtest.driver.adapter.shop.api.ShopApiDriver;
import com.optivem.eshop.systemtest.driver.adapter.shop.ui.ShopUiDriver;
import com.optivem.eshop.systemtest.driver.adapter.tax.TaxRealDriver;
import com.optivem.eshop.systemtest.driver.adapter.tax.TaxStubDriver;
import com.optivem.eshop.systemtest.infrastructure.playwright.BrowserLifecycleExtension;
import com.optivem.eshop.systemtest.dsl.core.app.ExternalSystemMode;
import com.optivem.testing.contexts.ChannelContext;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserLifecycleExtension.class)
public abstract class BaseConfigurableTest {
    protected Environment getFixedEnvironment() {
        return null;
    }

    protected ExternalSystemMode getFixedExternalSystemMode() {
        return null;
    }

    protected AppConfiguration loadConfiguration() {
        var fixedEnvironment = getFixedEnvironment();
        var fixedExternalSystemMode = getFixedExternalSystemMode();

        var environment = PropertyLoader.getEnvironment(fixedEnvironment);
        var externalSystemMode = PropertyLoader.getExternalSystemMode(fixedExternalSystemMode);
        return AppConfigurationLoader.load(environment, externalSystemMode);
    }

    protected AppDsl createAppDsl(AppConfiguration configuration) {
        return new AppDsl(
                configuration,
                () -> createShopDriver(configuration),
                () -> createErpDriver(configuration),
                () -> createTaxDriver(configuration),
                () -> createClockDriver(configuration)
        );
    }

    private ShopDriver createShopDriver(AppConfiguration configuration) {
        var channel = ChannelContext.get();
        if (ChannelType.UI.equals(channel)) {
            return new ShopUiDriver(configuration.getShopUiBaseUrl(), BrowserLifecycleExtension.getBrowser());
        } else if (ChannelType.API.equals(channel)) {
            return new ShopApiDriver(configuration.getShopApiBaseUrl());
        } else {
            throw new IllegalStateException("Unknown channel: " + channel);
        }
    }

    private ErpDriver createErpDriver(AppConfiguration configuration) {
        return switch (configuration.getExternalSystemMode()) {
            case REAL -> new ErpRealDriver(configuration.getErpBaseUrl());
            case STUB -> new ErpStubDriver(configuration.getErpBaseUrl());
        };
    }

    private TaxDriver createTaxDriver(AppConfiguration configuration) {
        return switch (configuration.getExternalSystemMode()) {
            case REAL -> new TaxRealDriver(configuration.getTaxBaseUrl());
            case STUB -> new TaxStubDriver(configuration.getTaxBaseUrl());
        };
    }

    private ClockDriver createClockDriver(AppConfiguration configuration) {
        return switch (configuration.getExternalSystemMode()) {
            case REAL -> new ClockRealDriver();
            case STUB -> new ClockStubDriver(configuration.getClockBaseUrl());
        };
    }
}

