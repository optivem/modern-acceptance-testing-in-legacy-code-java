package com.optivem.eshop.systemtest.core.erp.dsl;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.CreateProduct;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.GoToErp;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class ErpDsl implements Closeable {
    private final ErpDriver driver;
    private final UseCaseContext context;

    public ErpDsl(String baseUrl, UseCaseContext context) {
        this.driver = new ErpDriver(baseUrl);
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

