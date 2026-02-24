package com.optivem.eshop.systemtest.core;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.dsl.ClockDsl;
import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.dsl.ErpDsl;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.ShopDsl;
import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.dsl.TaxDsl;
import com.optivem.eshop.systemtest.infra.clock.driver.ClockRealDriver;
import com.optivem.eshop.systemtest.infra.clock.driver.ClockStubDriver;
import com.optivem.eshop.systemtest.infra.erp.driver.ErpRealDriver;
import com.optivem.eshop.systemtest.infra.erp.driver.ErpStubDriver;
import com.optivem.eshop.systemtest.infra.shop.driver.api.ShopApiDriver;
import com.optivem.eshop.systemtest.infra.shop.driver.ui.ShopUiDriver;
import com.optivem.eshop.systemtest.infra.tax.driver.TaxRealDriver;
import com.optivem.eshop.systemtest.infra.tax.driver.TaxStubDriver;
import com.optivem.commons.playwright.BrowserLifecycleExtension;
import com.optivem.commons.util.Closer;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.testing.contexts.ChannelContext;

import java.io.Closeable;
import java.util.function.Supplier;

public class SystemDsl implements Closeable {
    private final SystemConfiguration configuration;
    private final UseCaseContext context;

    private ShopDsl shop;
    private ErpDsl erp;
    private TaxDsl tax;
    private ClockDsl clock;

    private SystemDsl(SystemConfiguration configuration, UseCaseContext context) {
        this.configuration = configuration;
        this.context = context;
    }

    public SystemDsl(SystemConfiguration configuration) {
        this(configuration, new UseCaseContext(configuration.getExternalSystemMode()));
    }

    @Override
    public void close() {
        Closer.close(shop);
        Closer.close(erp);
        Closer.close(tax);
        Closer.close(clock);
    }

    public ShopDsl shop() {
        return getOrCreate(shop, () -> shop = new ShopDsl(createShopDriver(), context));
    }

    public ErpDsl erp() {
        return getOrCreate(erp, () -> erp = new ErpDsl(createErpDriver(), context));
    }

    public TaxDsl tax() {
        return getOrCreate(tax, () -> tax = new TaxDsl(createTaxDriver(), context));
    }

    public ClockDsl clock() {
        return getOrCreate(clock, () -> clock = new ClockDsl(createClockDriver(), context));
    }

    private ShopDriver createShopDriver() {
        var channel = ChannelContext.get();
        if (ChannelType.UI.equals(channel)) {
            return new ShopUiDriver(configuration.getShopUiBaseUrl(), BrowserLifecycleExtension.getBrowser());
        } else if (ChannelType.API.equals(channel)) {
            return new ShopApiDriver(configuration.getShopApiBaseUrl());
        } else {
            throw new IllegalStateException("Unknown channel: " + channel);
        }
    }

    private ErpDriver createErpDriver() {
        return switch (context.getExternalSystemMode()) {
            case REAL -> new ErpRealDriver(configuration.getErpBaseUrl());
            case STUB -> new ErpStubDriver(configuration.getErpBaseUrl());
        };
    }

    private TaxDriver createTaxDriver() {
        return switch (context.getExternalSystemMode()) {
            case REAL -> new TaxRealDriver(configuration.getTaxBaseUrl());
            case STUB -> new TaxStubDriver(configuration.getTaxBaseUrl());
        };
    }

    private ClockDriver createClockDriver() {
        return switch (context.getExternalSystemMode()) {
            case REAL -> new ClockRealDriver();
            case STUB -> new ClockStubDriver(configuration.getClockBaseUrl());
        };
    }

    private static <T> T getOrCreate(T instance, Supplier<T> supplier) {
        return instance != null ? instance : supplier.get();
    }
}

