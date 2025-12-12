package com.optivem.eshop.systemtest.core;

import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.core.erp.dsl.ErpDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.ShopDsl;
import com.optivem.eshop.systemtest.core.tax.dsl.TaxDsl;
import com.optivem.lang.Closer;

import java.io.Closeable;
import java.util.function.Supplier;

public class SystemDsl implements Closeable {
    private final Context context;
    private final SystemConfiguration configuration;

    private ShopDsl shop;
    private ErpDsl erp;
    private TaxDsl tax;

    public SystemDsl(SystemConfiguration configuration) {
        this.context = new Context();
        this.configuration = configuration;
    }

    @Override
    public void close() {
        Closer.close(shop);
        Closer.close(erp);
        Closer.close(tax);
    }

    public ShopDsl shop() {
        return getOrCreate(shop, () -> shop = new ShopDsl(context, configuration));
    }

    public ErpDsl erp() {
        return getOrCreate(erp, () -> erp = new ErpDsl(context, configuration));
    }

    public TaxDsl tax() {
        return getOrCreate(tax, () -> tax = new TaxDsl(context, configuration));
    }

    private static <T> T getOrCreate(T instance, Supplier<T> supplier) {
        return instance != null ? instance : supplier.get();
    }
}

