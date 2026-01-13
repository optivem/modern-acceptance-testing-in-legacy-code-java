package com.optivem.eshop.systemtest.contracttests.erp;

import com.optivem.commons.dsl.ExternalSystemMode;

public class ErpStubContractTest extends BaseErpContractTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.STUB;
    }
}
