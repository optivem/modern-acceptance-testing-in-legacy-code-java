package com.optivem.eshop.systemtest.dsl.external.tax;

import com.optivem.eshop.systemtest.dsl.DslConfiguration;
import com.optivem.eshop.systemtest.dsl.external.tax.driver.TaxDriver;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.dsl.external.tax.commands.GoToTax;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class TaxDsl implements Closeable {
    private final TaxDriver driver;
    private final Context context;

    public TaxDsl(Context context, DslConfiguration configuration) {
        this.driver = createDriver(configuration);
        this.context = context;
    }

    private static TaxDriver createDriver(DslConfiguration configuration) {
        return new TaxDriver(configuration.getTaxBaseUrl());
    }

    @Override
    public void close() {
        Closer.close(driver);
    }

    public GoToTax goToTax() {
        return new GoToTax(driver, context);
    }
}

