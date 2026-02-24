package com.optivem.eshop.systemtest.contracttests.v7.clock;

import com.optivem.eshop.systemtest.dsl.core.system.shared.ExternalSystemMode;

public class ClockRealContractTest extends BaseClockContractTest {

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}

