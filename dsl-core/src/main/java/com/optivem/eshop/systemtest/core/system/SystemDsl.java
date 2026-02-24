package com.optivem.eshop.systemtest.core.system;

import com.optivem.eshop.systemtest.driver.api.clock.ClockDriver;
import com.optivem.eshop.systemtest.core.system.clock.dsl.ClockDsl;
import com.optivem.eshop.systemtest.driver.api.erp.ErpDriver;
import com.optivem.eshop.systemtest.core.system.erp.dsl.ErpDsl;
import com.optivem.eshop.systemtest.driver.api.shop.ShopDriver;
import com.optivem.eshop.systemtest.core.system.shop.dsl.ShopDsl;
import com.optivem.eshop.systemtest.driver.api.tax.TaxDriver;
import com.optivem.eshop.systemtest.core.system.tax.dsl.TaxDsl;
import com.optivem.commons.util.Closer;
import com.optivem.commons.dsl.UseCaseContext;

import java.io.Closeable;
import java.util.function.Supplier;

public class SystemDsl implements Closeable {
    private final UseCaseContext context;
    private final Supplier<ShopDriver> shopDriverSupplier;
    private final Supplier<ErpDriver> erpDriverSupplier;
    private final Supplier<TaxDriver> taxDriverSupplier;
    private final Supplier<ClockDriver> clockDriverSupplier;

    private ShopDriver shopDriver;
    private ErpDriver erpDriver;
    private TaxDriver taxDriver;
    private ClockDriver clockDriver;

    private ShopDsl shop;
    private ErpDsl erp;
    private TaxDsl tax;
    private ClockDsl clock;

    private SystemDsl(UseCaseContext context,
                      Supplier<ShopDriver> shopDriverSupplier,
                      Supplier<ErpDriver> erpDriverSupplier,
                      Supplier<TaxDriver> taxDriverSupplier,
                      Supplier<ClockDriver> clockDriverSupplier) {
        this.context = context;
        this.shopDriverSupplier = shopDriverSupplier;
        this.erpDriverSupplier = erpDriverSupplier;
        this.taxDriverSupplier = taxDriverSupplier;
        this.clockDriverSupplier = clockDriverSupplier;
    }

    public SystemDsl(SystemConfiguration configuration, ShopDriver shopDriver, ErpDriver erpDriver, TaxDriver taxDriver, ClockDriver clockDriver) {
        this(
                configuration,
                () -> shopDriver,
                () -> erpDriver,
                () -> taxDriver,
                () -> clockDriver
        );
    }

    public SystemDsl(SystemConfiguration configuration,
                     Supplier<ShopDriver> shopDriverSupplier,
                     Supplier<ErpDriver> erpDriverSupplier,
                     Supplier<TaxDriver> taxDriverSupplier,
                     Supplier<ClockDriver> clockDriverSupplier) {
        this(new UseCaseContext(configuration.getExternalSystemMode()), shopDriverSupplier, erpDriverSupplier, taxDriverSupplier, clockDriverSupplier);
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
        return getOrCreate(shop, () -> {
            shopDriver = getOrCreate(shopDriver, shopDriverSupplier);
            return shop = new ShopDsl(shopDriver, context);
        });
    }

    public ErpDsl erp() {
        return getOrCreate(erp, () -> {
            erpDriver = getOrCreate(erpDriver, erpDriverSupplier);
            return erp = new ErpDsl(erpDriver, context);
        });
    }

    public TaxDsl tax() {
        return getOrCreate(tax, () -> {
            taxDriver = getOrCreate(taxDriver, taxDriverSupplier);
            return tax = new TaxDsl(taxDriver, context);
        });
    }

    public ClockDsl clock() {
        return getOrCreate(clock, () -> {
            clockDriver = getOrCreate(clockDriver, clockDriverSupplier);
            return clock = new ClockDsl(clockDriver, context);
        });
    }

    private static <T> T getOrCreate(T instance, Supplier<T> supplier) {
        return instance != null ? instance : supplier.get();
    }
}

