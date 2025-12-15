package com.optivem.eshop.systemtest.core.tax.dsl;

import com.optivem.eshop.systemtest.core.SystemConfiguration;
import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.GoToTax;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class TaxDsl implements Closeable {
    private final TaxDriver driver;
    private final UseCaseContext context;

    public TaxDsl(UseCaseContext context, SystemConfiguration configuration) {
        this.driver = createDriver(configuration);
        this.context = context;
    }

    private static TaxDriver createDriver(SystemConfiguration configuration) {
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

