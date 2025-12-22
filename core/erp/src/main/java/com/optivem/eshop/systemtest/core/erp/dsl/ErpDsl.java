package com.optivem.eshop.systemtest.core.erp.dsl;

import com.optivem.eshop.systemtest.core.erp.driver.real.ErpRealDriver;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.ReturnsProduct;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.GoToErp;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class ErpDsl implements Closeable {
    private final ErpRealDriver driver;
    private final UseCaseContext context;

    public ErpDsl(String baseUrl, UseCaseContext context) {
        this.driver = new ErpRealDriver(baseUrl);
        this.context = context;
    }

    @Override
    public void close() {
        Closer.close(driver);
    }

    public GoToErp goToErp() {
        return new GoToErp(driver, context);
    }

    public ReturnsProduct returnsProduct() {
        return new ReturnsProduct(driver, context);
    }
}

