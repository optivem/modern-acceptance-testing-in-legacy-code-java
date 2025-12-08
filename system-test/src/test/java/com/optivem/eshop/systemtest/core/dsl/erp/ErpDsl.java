package com.optivem.eshop.systemtest.core.dsl.erp;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.confirm.ConfirmErpOpened;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.confirm.ConfirmProductCreated;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.execute.CreateProduct;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.execute.GoToErp;

public class ErpDsl {
    private final ErpApiDriver driver;
    private final DslContext context;

    public ErpDsl(ErpApiDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public GoToErp goToErp() {
        return new GoToErp(driver, context);
    }

    public void confirmErpOpened() {
        new ConfirmErpOpened(driver, context).execute();
    }

    public CreateProduct createProduct() {
        return new CreateProduct(driver, context);
    }

    public ConfirmProductCreated confirmProductCreated() {
        return new ConfirmProductCreated(driver, context);
    }
}

