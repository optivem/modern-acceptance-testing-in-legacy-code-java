package com.optivem.eshop.systemtest.contracttests.clock;

import com.optivem.testing.dsl.ExternalSystemMode;
import org.junit.jupiter.api.Disabled;

@Disabled
public class ClockStubContractTest extends BaseClockContractTest {

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.STUB;
    }
}

