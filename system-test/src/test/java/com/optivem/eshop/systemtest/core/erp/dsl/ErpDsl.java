package com.optivem.eshop.systemtest.core.erp.dsl;

import com.optivem.eshop.systemtest.core.SystemConfiguration;
import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.CreateProduct;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.GoToErp;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class ErpDsl implements Closeable {
    private final ErpDriver driver;
    private final Context context;

    public ErpDsl(Context context, SystemConfiguration configuration) {
        this.driver = createDriver(configuration);
        this.context = context;
    }

    private static ErpDriver createDriver(SystemConfiguration configuration) {
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

