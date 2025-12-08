package com.optivem.eshop.systemtest.core.dsl.tax;

import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.tax.commands.confirm.ConfirmTaxOpened;
import com.optivem.eshop.systemtest.core.dsl.tax.commands.execute.GoToTax;

public class TaxDsl {
    private final TaxApiDriver driver;
    private final DslContext context;

    public TaxDsl(TaxApiDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public void goToTax() {
        new GoToTax(driver, context).execute();
    }

    public void confirmTaxOpened() {
        new ConfirmTaxOpened(driver, context).execute();
    }
}

