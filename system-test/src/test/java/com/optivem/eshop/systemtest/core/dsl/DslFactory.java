package com.optivem.eshop.systemtest.core.dsl;

import com.optivem.eshop.systemtest.core.dsl.commons.context.Context;
import com.optivem.eshop.systemtest.core.dsl.erp.ErpDsl;
import com.optivem.eshop.systemtest.core.dsl.shop.ShopDsl;
import com.optivem.eshop.systemtest.core.dsl.tax.TaxDsl;

public class DslFactory {
    private final Context context;

    public DslFactory(Context context) {
        this.context = context;
    }

    public DslFactory() {
        this(new Context());
    }

    public ShopDsl createShopDsl() {
        return new ShopDsl(context);
    }

    public ErpDsl createErpDsl() {
        return new ErpDsl(context);
    }

    public TaxDsl createTaxDsl() {
        return new TaxDsl(context);
    }
}
