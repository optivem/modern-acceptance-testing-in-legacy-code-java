package com.optivem.eshop.systemtest.core.erp.dsl;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.driver.ErpRealDriver;
import com.optivem.eshop.systemtest.core.erp.driver.ErpStubDriver;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.GetProduct;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.GoToErp;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.ReturnsProduct;
import com.optivem.lang.Closer;
import com.optivem.testing.dsl.UseCaseContext;

public class ErpDsl implements AutoCloseable {
    protected final ErpDriver driver;
    protected final UseCaseContext context;

    private ErpDsl(ErpDriver driver, UseCaseContext context) {
        this.driver = driver;
        this.context = context;
    }

    public ErpDsl(String baseUrl, UseCaseContext context) {
        this(createDriver(baseUrl, context), context);
    }

    private static ErpDriver createDriver(String baseUrl, UseCaseContext context) {
        return switch (context.getExternalSystemMode()) {
            case REAL -> new ErpRealDriver(baseUrl);
            case STUB -> new ErpStubDriver(baseUrl);
        };
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

    public GetProduct getProduct() {
        return new GetProduct(driver, context);
    }
}

