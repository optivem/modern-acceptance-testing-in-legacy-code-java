package com.optivem.eshop.systemtest.dsl.core.system.erp.dsl;

import com.optivem.eshop.systemtest.driver.api.erp.ErpDriver;
import com.optivem.eshop.systemtest.dsl.core.system.erp.dsl.usecases.GetProduct;
import com.optivem.eshop.systemtest.dsl.core.system.erp.dsl.usecases.GoToErp;
import com.optivem.eshop.systemtest.dsl.core.system.erp.dsl.usecases.ReturnsProduct;
import com.optivem.commons.util.Closer;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;

public class ErpDsl implements AutoCloseable {
    protected final ErpDriver driver;
    protected final UseCaseContext context;

    public ErpDsl(ErpDriver driver, UseCaseContext context) {
        this.driver = driver;
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

    public GetProduct getProduct() {
        return new GetProduct(driver, context);
    }
}


