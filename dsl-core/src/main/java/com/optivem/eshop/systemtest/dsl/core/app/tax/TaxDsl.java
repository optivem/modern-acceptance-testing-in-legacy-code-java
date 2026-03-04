package com.optivem.eshop.systemtest.dsl.core.app.tax;

import com.optivem.eshop.systemtest.driver.port.tax.TaxDriver;
import com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.GetTaxRate;
import com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.GoToTax;
import com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.ReturnsTaxRate;
import com.optivem.common.Closer;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseContext;

import java.io.Closeable;

public class TaxDsl implements Closeable {
    private final TaxDriver driver;
    private final UseCaseContext context;

    public TaxDsl(TaxDriver driver, UseCaseContext context) {
        this.driver = driver;
        this.context = context;
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


