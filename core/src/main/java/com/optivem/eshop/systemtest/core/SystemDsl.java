package com.optivem.eshop.systemtest.core;

import com.optivem.eshop.systemtest.core.clock.dsl.BaseClockDsl;
import com.optivem.eshop.systemtest.core.clock.dsl.ClockRealDsl;
import com.optivem.eshop.systemtest.core.clock.dsl.ClockStubDsl;
import com.optivem.eshop.systemtest.core.erp.dsl.BaseErpDsl;
import com.optivem.eshop.systemtest.core.erp.dsl.ErpRealDsl;
import com.optivem.eshop.systemtest.core.erp.dsl.ErpStubDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.ShopDsl;
import com.optivem.eshop.systemtest.core.tax.dsl.TaxDsl;
import com.optivem.eshop.systemtest.core.tax.dsl.TaxRealDsl;
import com.optivem.eshop.systemtest.core.tax.dsl.TaxStubDsl;
import com.optivem.lang.Closer;
import com.optivem.testing.dsl.UseCaseContext;

import java.io.Closeable;
import java.util.function.Supplier;

public class SystemDsl implements Closeable {
    private final SystemConfiguration configuration;
    private final UseCaseContext context;

    private ShopDsl shop;
    private BaseErpDsl erp;
    private TaxDsl tax;
    private BaseClockDsl clock;

    private SystemDsl(SystemConfiguration configuration, UseCaseContext context) {
        this.configuration = configuration;
        this.context = context;
    }

    public SystemDsl(SystemConfiguration configuration) {
        this(configuration, new UseCaseContext(configuration.getExternalSystemMode()));
    }

    private static <T> T getOrCreate(T instance, Supplier<T> supplier) {
        return instance != null ? instance : supplier.get();
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

    public BaseErpDsl erp() {
        if (erp == null) {
            erp = switch (configuration.getExternalSystemMode()) {
                case REAL -> new ErpRealDsl(configuration.getErpBaseUrl(), context);
                case STUB -> new ErpStubDsl(configuration.getErpBaseUrl(), context);
            };
        }
        return erp;
    }

    public TaxDsl tax() {
        if (tax == null) {
            tax = switch (configuration.getExternalSystemMode()) {
                case REAL -> new TaxRealDsl(configuration.getTaxBaseUrl(), context);
                case STUB -> new TaxStubDsl(configuration.getTaxBaseUrl(), context);
            };
        }
        return tax;
    }

    public BaseClockDsl clock() {
        if (clock == null) {
            clock = switch (configuration.getExternalSystemMode()) {
                case REAL -> new ClockRealDsl(context);
                case STUB -> new ClockStubDsl(configuration.getClockBaseUrl(), context);
            };
        }
        return clock;
    }
}

