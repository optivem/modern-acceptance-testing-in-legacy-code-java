package com.optivem.eshop.systemtest.contracttests.clock;

import com.optivem.eshop.systemtest.contracttests.base.BaseExternalSystemContractTest;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public abstract class BaseClockContractTest extends BaseExternalSystemContractTest {

    @Test
    void shouldBeAbleToGetTime() {
        var beforeCall = Instant.now();

        app.clock().getTime()
                .execute()
                .shouldSucceed()
                .timeIsNotNull()
                .timeIsAfter(beforeCall.minusSeconds(1));

        var afterCall = Instant.now();
    }

    @Test
    void shouldBeAbleToSetAndGetTime() {
        var fixedTime = Instant.parse("2025-12-24T10:00:00Z");

        app.clock().returnsTime()
                .time(fixedTime)
                .execute()
                .shouldSucceed();

        app.clock().getTime()
                .execute()
                .shouldSucceed()
                .timeIsNotNull();
    }
}

