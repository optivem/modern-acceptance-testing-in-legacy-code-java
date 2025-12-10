package com.optivem.eshop.systemtest.core.dsl.erp;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.context.Context;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.CreateProduct;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.GoToErp;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class ErpDsl implements Closeable {
    private final ErpApiDriver driver;
    private final Context context;

    public ErpDsl(Context context) {
        this.driver = DriverFactory.createErpApiDriver();
        this.context = context;
    }

    @Override
    public void close() {
        Closer.close(driver);
    }

    public GoToErp goToErp() {
        return new GoToErp(driver, context);
    }

    public CreateProduct createProduct() {
        return new CreateProduct(driver, context);
    }
}

