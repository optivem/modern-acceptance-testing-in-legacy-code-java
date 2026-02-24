package com.optivem.eshop.systemtest.core;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.dsl.ClockDsl;
import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.dsl.ErpDsl;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.ShopDsl;
import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.dsl.TaxDsl;
import com.optivem.commons.util.Closer;
import com.optivem.commons.dsl.UseCaseContext;

import java.io.Closeable;
import java.util.function.Supplier;

public class SystemDsl implements Closeable {
    private final UseCaseContext context;
    private final ShopDriver shopDriver;
    private final ErpDriver erpDriver;
    private final TaxDriver taxDriver;
    private final ClockDriver clockDriver;

    private ShopDsl shop;
    private ErpDsl erp;
    private TaxDsl tax;
    private ClockDsl clock;

    private SystemDsl(UseCaseContext context, ShopDriver shopDriver, ErpDriver erpDriver, TaxDriver taxDriver, ClockDriver clockDriver) {
        this.context = context;
        this.shopDriver = shopDriver;
        this.erpDriver = erpDriver;
        this.taxDriver = taxDriver;
        this.clockDriver = clockDriver;
    }

    public SystemDsl(SystemConfiguration configuration, ShopDriver shopDriver, ErpDriver erpDriver, TaxDriver taxDriver, ClockDriver clockDriver) {
        this(new UseCaseContext(configuration.getExternalSystemMode()), shopDriver, erpDriver, taxDriver, clockDriver);
    }

    @Override
    public void close() {
        if (shop != null) {
            Closer.close(shop);
        } else {
            Closer.close(shopDriver);
        }

        if (erp != null) {
            Closer.close(erp);
        } else {
            Closer.close(erpDriver);
        }

        if (tax != null) {
            Closer.close(tax);
        } else {
            Closer.close(taxDriver);
        }

        if (clock != null) {
            Closer.close(clock);
        } else {
            Closer.close(clockDriver);
        }
    }

    public ShopDsl shop() {
        return getOrCreate(shop, () -> shop = new ShopDsl(shopDriver, context));
    }

    public ErpDsl erp() {
        return getOrCreate(erp, () -> erp = new ErpDsl(erpDriver, context));
    }

    public TaxDsl tax() {
        return getOrCreate(tax, () -> tax = new TaxDsl(taxDriver, context));
    }

    public ClockDsl clock() {
        return getOrCreate(clock, () -> clock = new ClockDsl(clockDriver, context));
    }

    private static <T> T getOrCreate(T instance, Supplier<T> supplier) {
        return instance != null ? instance : supplier.get();
    }
}

