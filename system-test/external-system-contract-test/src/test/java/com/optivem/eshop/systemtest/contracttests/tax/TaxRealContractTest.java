package com.optivem.eshop.systemtest.contracttests.tax;

import com.optivem.test.dsl.ExternalSystemMode;

public class TaxRealContractTest extends BaseTaxContractTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}
