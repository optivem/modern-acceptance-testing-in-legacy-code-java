package com.optivem.eshop.systemtest.contracttests.clock;

import com.optivem.commons.dsl.ExternalSystemMode;
import org.junit.jupiter.api.Test;


public class ClockStubContractTest extends BaseClockContractTest {

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.STUB;
    }

    @Test
    void shouldBeAbleToGetConfiguredTime() {
        app.clock().returnsTime()
                .time("2024-01-02T09:00:00Z")
                .execute()
                .shouldSucceed();

        app.clock().getTime()
                .execute()
                .shouldSucceed()
                .time("2024-01-02T09:00:00Z");
    }
}

