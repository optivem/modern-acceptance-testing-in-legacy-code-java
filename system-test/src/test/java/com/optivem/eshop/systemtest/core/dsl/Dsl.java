package com.optivem.eshop.systemtest.core.dsl;

import com.optivem.eshop.systemtest.core.dsl.commons.context.Context;
import com.optivem.eshop.systemtest.core.dsl.erp.ErpDsl;
import com.optivem.eshop.systemtest.core.dsl.shop.ShopDsl;
import com.optivem.eshop.systemtest.core.dsl.tax.TaxDsl;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class Dsl implements Closeable {
    private final Context context;
    private ShopDsl shop;
    private ErpDsl erp;
    private TaxDsl tax;

    public Dsl() {
        this.context = new Context();
    }

    public ShopDsl shop() {
        if (shop == null) {
            shop = new ShopDsl(context);
        }
        return shop;
    }

    public ErpDsl erp() {
        if (erp == null) {
            erp = new ErpDsl(context);
        }
        return erp;
    }

    public TaxDsl tax() {
        if (tax == null) {
            tax = new TaxDsl(context);
        }
        return tax;
    }

    @Override
    public void close() {
        Closer.close(shop);
        Closer.close(erp);
        Closer.close(tax);
    }
}

