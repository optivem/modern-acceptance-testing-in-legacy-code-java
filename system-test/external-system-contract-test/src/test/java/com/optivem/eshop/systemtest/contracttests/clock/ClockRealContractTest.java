package com.optivem.eshop.systemtest.contracttests.clock;

import com.optivem.commons.dsl.ExternalSystemMode;

public class ClockRealContractTest extends BaseClockContractTest {

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}

