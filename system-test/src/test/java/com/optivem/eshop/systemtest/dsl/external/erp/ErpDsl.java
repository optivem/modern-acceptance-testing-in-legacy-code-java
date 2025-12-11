package com.optivem.eshop.systemtest.dsl.external.erp;

import com.optivem.eshop.systemtest.dsl.DslConfiguration;
import com.optivem.eshop.systemtest.dsl.external.erp.driver.ErpDriver;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.dsl.external.erp.commands.CreateProduct;
import com.optivem.eshop.systemtest.dsl.external.erp.commands.GoToErp;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class ErpDsl implements Closeable {
    private final ErpDriver driver;
    private final Context context;

    public ErpDsl(Context context, DslConfiguration configuration) {
        this.driver = createDriver(configuration);
        this.context = context;
    }

    private static ErpDriver createDriver(DslConfiguration configuration) {
        return new ErpDriver(configuration.getErpBaseUrl());
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

