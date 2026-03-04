package com.optivem.eshop.systemtest.smoketests.v6.external;

import com.optivem.eshop.systemtest.base.v6.BaseScenarioDslTest;
import org.junit.jupiter.api.Test;

class TaxSmokeTest extends BaseScenarioDslTest {
    @Test
    void shouldBeAbleToGoToTax() {
        background.taxRunning();
    }
}

