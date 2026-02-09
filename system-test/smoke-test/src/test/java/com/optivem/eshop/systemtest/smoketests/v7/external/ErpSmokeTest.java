package com.optivem.eshop.systemtest.smoketests.v7.external;

import com.optivem.eshop.systemtest.base.v5.BaseSystemDslTest;
import org.junit.jupiter.api.Test;

class ErpSmokeTest extends BaseSystemDslTest {

    @Test
    void shouldBeAbleToGoToErp() {
        app.erp().goToErp()
                .execute()
                .shouldSucceed();
    }
}

