package com.optivem.eshop.systemtest.core.tax.dsl;

import com.optivem.eshop.systemtest.core.tax.driver.TaxStubDriver;
import com.optivem.testing.dsl.UseCaseContext;

public class TaxStubDsl extends TaxDsl {

    public TaxStubDsl(String baseUrl, UseCaseContext context) {
        super(new TaxStubDriver(baseUrl), context);
    }
}

