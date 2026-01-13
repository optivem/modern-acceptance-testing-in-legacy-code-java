package com.optivem.eshop.systemtest.contracttests.tax;

import com.optivem.commons.dsl.ExternalSystemMode;

public class TaxRealContractTest extends BaseTaxContractTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}
