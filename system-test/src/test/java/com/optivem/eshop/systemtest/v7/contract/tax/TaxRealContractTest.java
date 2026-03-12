package com.optivem.eshop.systemtest.v7.contract.tax;

import com.optivem.eshop.dsl.port.ExternalSystemMode;

public class TaxRealContractTest extends BaseTaxContractTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}



