package com.optivem.eshop.systemtest.contracttests.clock;

import com.optivem.test.dsl.ExternalSystemMode;

public class ClockRealContractTest extends BaseClockContractTest {

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}

