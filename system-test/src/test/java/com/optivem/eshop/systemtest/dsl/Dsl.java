package com.optivem.eshop.systemtest.dsl;

import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.dsl.external.erp.ErpDsl;
import com.optivem.eshop.systemtest.dsl.shop.ShopDsl;
import com.optivem.eshop.systemtest.dsl.external.tax.TaxDsl;
import com.optivem.lang.Closer;
import org.springframework.boot.test.context.TestConfiguration;

import java.io.Closeable;

public class Dsl implements Closeable {
    private final Context context;
    private final DslConfiguration configuration;

    private ShopDsl shop;
    private ErpDsl erp;
    private TaxDsl tax;

    public Dsl(DslConfiguration configuration) {
        this.context = new Context();
        this.configuration = configuration;
    }

    public ShopDsl shop() {
        if (shop == null) {
            shop = new ShopDsl(context, configuration);
        }
        return shop;
    }

    public ErpDsl erp() {
        if (erp == null) {
            erp = new ErpDsl(context, configuration);
        }
        return erp;
    }

    public TaxDsl tax() {
        if (tax == null) {
            tax = new TaxDsl(context, configuration);
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

