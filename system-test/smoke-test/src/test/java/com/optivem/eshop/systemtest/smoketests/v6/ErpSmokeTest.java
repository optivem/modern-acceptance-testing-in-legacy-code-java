package com.optivem.eshop.systemtest.smoketests.v6;

import com.optivem.eshop.systemtest.base.v5.BaseSystemTest;
import org.junit.jupiter.api.Test;

public class ErpSmokeTest extends BaseSystemTest {

    @Test
    void shouldBeAbleToGoToErp() {
        app.erp().goToErp()
                .execute()
                .shouldSucceed();
    }
}

