package com.optivem.eshop.systemtest.contracttests.v7.clock;

import com.optivem.eshop.systemtest.dsl.core.app.shared.ExternalSystemMode;
import org.junit.jupiter.api.Test;


class ClockStubContractTest extends BaseClockContractTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.STUB;
    }

    @Test
    void shouldBeAbleToGetConfiguredTime() {
        scenario
                .given().clock().withTime("2024-01-02T09:00:00Z")
                .then().clock().hasTime("2024-01-02T09:00:00Z");
    }
}

