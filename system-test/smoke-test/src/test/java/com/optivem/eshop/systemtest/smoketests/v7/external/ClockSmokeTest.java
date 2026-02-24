package com.optivem.eshop.systemtest.smoketests.v7.external;

import com.optivem.eshop.systemtest.base.v5.BaseSystemDslTest;
import org.junit.jupiter.api.Test;

class ClockSmokeTest extends BaseSystemDslTest {

    @Test
    void shouldBeAbleToGoToClock() {
        app.clock().goToClock()
                .execute()
                .shouldSucceed();
    }
}


