package com.optivem.eshop.systemtest.core.tax.dsl;

import com.optivem.eshop.systemtest.core.tax.driver.TaxRealDriver;
import com.optivem.testing.dsl.UseCaseContext;

public class TaxRealDsl extends TaxDsl {

    public TaxRealDsl(String baseUrl, UseCaseContext context) {
        super(new TaxRealDriver(baseUrl), context);
    }
}

