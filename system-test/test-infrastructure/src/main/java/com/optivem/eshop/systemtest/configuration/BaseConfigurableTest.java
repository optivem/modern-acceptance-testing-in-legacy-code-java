package com.optivem.eshop.systemtest.configuration;

import com.optivem.eshop.systemtest.core.system.SystemDsl;
import com.optivem.eshop.systemtest.core.system.SystemConfiguration;
import com.optivem.eshop.systemtest.driver.api.clock.ClockDriver;
import com.optivem.eshop.systemtest.driver.api.erp.ErpDriver;
import com.optivem.eshop.systemtest.core.system.shop.ChannelType;
import com.optivem.eshop.systemtest.driver.api.shop.ShopDriver;
import com.optivem.eshop.systemtest.driver.api.tax.TaxDriver;
import com.optivem.eshop.systemtest.driver.core.clock.driver.ClockRealDriver;
import com.optivem.eshop.systemtest.driver.core.clock.driver.ClockStubDriver;
import com.optivem.eshop.systemtest.driver.core.erp.driver.ErpRealDriver;
import com.optivem.eshop.systemtest.driver.core.erp.driver.ErpStubDriver;
import com.optivem.eshop.systemtest.driver.core.shop.driver.api.ShopApiDriver;
import com.optivem.eshop.systemtest.driver.core.shop.driver.ui.ShopUiDriver;
import com.optivem.eshop.systemtest.driver.core.tax.driver.TaxRealDriver;
import com.optivem.eshop.systemtest.driver.core.tax.driver.TaxStubDriver;
import com.optivem.commons.playwright.BrowserLifecycleExtension;
import com.optivem.commons.dsl.ExternalSystemMode;
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

    protected SystemConfiguration loadConfiguration() {
        var fixedEnvironment = getFixedEnvironment();
        var fixedExternalSystemMode = getFixedExternalSystemMode();

        var environment = PropertyLoader.getEnvironment(fixedEnvironment);
        var externalSystemMode = PropertyLoader.getExternalSystemMode(fixedExternalSystemMode);
        return SystemConfigurationLoader.load(environment, externalSystemMode);
    }

    protected SystemDsl createSystemDsl(SystemConfiguration configuration) {
        return new SystemDsl(
                configuration,
                () -> createShopDriver(configuration),
                () -> createErpDriver(configuration),
                () -> createTaxDriver(configuration),
                () -> createClockDriver(configuration)
        );
    }

    private ShopDriver createShopDriver(SystemConfiguration configuration) {
        var channel = ChannelContext.get();
        if (ChannelType.UI.equals(channel)) {
            return new ShopUiDriver(configuration.getShopUiBaseUrl(), BrowserLifecycleExtension.getBrowser());
        } else if (ChannelType.API.equals(channel)) {
            return new ShopApiDriver(configuration.getShopApiBaseUrl());
        } else {
            throw new IllegalStateException("Unknown channel: " + channel);
        }
    }

    private ErpDriver createErpDriver(SystemConfiguration configuration) {
        return switch (configuration.getExternalSystemMode()) {
            case REAL -> new ErpRealDriver(configuration.getErpBaseUrl());
            case STUB -> new ErpStubDriver(configuration.getErpBaseUrl());
        };
    }

    private TaxDriver createTaxDriver(SystemConfiguration configuration) {
        return switch (configuration.getExternalSystemMode()) {
            case REAL -> new TaxRealDriver(configuration.getTaxBaseUrl());
            case STUB -> new TaxStubDriver(configuration.getTaxBaseUrl());
        };
    }

    private ClockDriver createClockDriver(SystemConfiguration configuration) {
        return switch (configuration.getExternalSystemMode()) {
            case REAL -> new ClockRealDriver();
            case STUB -> new ClockStubDriver(configuration.getClockBaseUrl());
        };
    }
}
