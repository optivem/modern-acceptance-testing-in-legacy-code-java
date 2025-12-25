package com.optivem.eshop.systemtest.smoketests.v5;

import com.optivem.eshop.systemtest.base.v5.BaseSystemTest;
import org.junit.jupiter.api.Test;

public class TaxSmokeTest extends BaseSystemTest {

    @Test
    void shouldBeAbleToGoToTax() {
        app.tax().goToTax()
                .execute()
                .shouldSucceed();
    }
}

