package com.optivem.eshop.systemtest.core.dsl.tax;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.context.Context;
import com.optivem.eshop.systemtest.core.dsl.tax.commands.GoToTax;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class TaxDsl implements Closeable {
    private final TaxApiDriver driver;
    private final Context context;

    public TaxDsl(Context context) {
        this.driver = DriverFactory.createTaxApiDriver();
        this.context = context;
    }

    @Override
    public void close() {
        Closer.close(driver);
    }

    public GoToTax goToTax() {
        return new GoToTax(driver, context);
    }
}

