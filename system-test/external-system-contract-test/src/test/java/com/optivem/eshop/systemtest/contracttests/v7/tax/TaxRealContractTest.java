package com.optivem.eshop.systemtest.contracttests.v7.tax;

import com.optivem.eshop.systemtest.dsl.core.system.shared.ExternalSystemMode;

public class TaxRealContractTest extends BaseTaxContractTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}
