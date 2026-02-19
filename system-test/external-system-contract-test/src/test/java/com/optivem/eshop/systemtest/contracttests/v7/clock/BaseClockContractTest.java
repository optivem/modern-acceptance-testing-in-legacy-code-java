package com.optivem.eshop.systemtest.contracttests.v7.clock;

import com.optivem.eshop.systemtest.contracttests.v7.base.BaseExternalSystemContractTest;
import org.junit.jupiter.api.Test;


public abstract class BaseClockContractTest extends BaseExternalSystemContractTest {

    @Test
    void shouldBeAbleToGetTime() {
        app.clock().returnsTime()
                .time("2024-06-15T12:00:00Z")
                .execute()
                .shouldSucceed();

        app.clock().getTime()
                .execute()
                .shouldSucceed()
                .timeIsNotNull();
    }
}
