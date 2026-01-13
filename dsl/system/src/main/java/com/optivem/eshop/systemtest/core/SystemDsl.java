package com.optivem.eshop.systemtest.core;

import com.optivem.eshop.systemtest.core.clock.dsl.ClockDsl;
import com.optivem.eshop.systemtest.core.erp.dsl.ErpDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.ShopDsl;
import com.optivem.eshop.systemtest.core.tax.dsl.TaxDsl;
import com.optivem.lang.Closer;
import com.optivem.test.dsl.UseCaseContext;

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
        return getOrCreate(shop, () -> shop = new ShopDsl(configuration.getShopUiBaseUrl(), configuration.getShopApiBaseUrl(), context));
    }

    public ErpDsl erp() {
        return getOrCreate(erp, () -> erp = new ErpDsl(configuration.getErpBaseUrl(), context));
    }

    public TaxDsl tax() {
        return getOrCreate(tax, () -> tax = new TaxDsl(configuration.getTaxBaseUrl(), context));
    }

    public ClockDsl clock() {
        return getOrCreate(clock, () -> clock = new ClockDsl(configuration.getClockBaseUrl(), context));
    }

    private static <T> T getOrCreate(T instance, Supplier<T> supplier) {
        return instance != null ? instance : supplier.get();
    }
}

