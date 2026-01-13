package com.optivem.eshop.systemtest.core.tax.dsl;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.driver.TaxRealDriver;
import com.optivem.eshop.systemtest.core.tax.driver.TaxStubDriver;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.GetTaxRate;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.GoToTax;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.ReturnsTaxRate;
import com.optivem.lang.Closer;
import com.optivem.test.dsl.UseCaseContext;

import java.io.Closeable;

public class TaxDsl implements Closeable {
    private final TaxDriver driver;
    private final UseCaseContext context;

    private TaxDsl(TaxDriver driver, UseCaseContext context) {
        this.driver = driver;
        this.context = context;
    }

    public TaxDsl(String baseUrl, UseCaseContext context) {
        this(createDriver(baseUrl, context), context);
    }

    private static TaxDriver createDriver(String baseUrl, UseCaseContext context) {
        return switch (context.getExternalSystemMode()) {
            case REAL -> new TaxRealDriver(baseUrl);
            case STUB -> new TaxStubDriver(baseUrl);
        };
    }

    @Override
    public void close() {
        Closer.close(driver);
    }

    public GoToTax goToTax() {
        return new GoToTax(driver, context);
    }

    public ReturnsTaxRate returnsTaxRate() {
        return new ReturnsTaxRate(driver, context);
    }

    public GetTaxRate getTaxRate() {
        return new GetTaxRate(driver, context);
    }
}

