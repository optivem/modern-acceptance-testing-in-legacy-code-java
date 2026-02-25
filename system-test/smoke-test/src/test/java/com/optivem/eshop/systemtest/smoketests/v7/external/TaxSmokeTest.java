package com.optivem.eshop.systemtest.smoketests.v7.external;

import com.optivem.eshop.systemtest.base.v5.BaseSystemDslTest;
import org.junit.jupiter.api.Test;

class TaxSmokeTest extends BaseSystemDslTest {
    @Test
    void shouldBeAbleToGoToTax() {
        app.tax().goToTax()
                .execute()
                .shouldSucceed();
    }
}


