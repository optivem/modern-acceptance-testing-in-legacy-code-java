package com.optivem.eshop.systemtest.contracttests.v7.erp;

import com.optivem.eshop.systemtest.dsl.core.app.ExternalSystemMode;

public class ErpStubContractTest extends BaseErpContractTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.STUB;
    }
}

