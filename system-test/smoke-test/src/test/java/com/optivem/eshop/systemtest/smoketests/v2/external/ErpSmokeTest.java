package com.optivem.eshop.systemtest.smoketests.v2.external;

import com.optivem.eshop.systemtest.base.v2.BaseClientTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.common.util.ResultAssert.assertThatResult;

class ErpSmokeTest extends BaseClientTest {
    @BeforeEach
    void setUp() {
        setUpExternalClients();
    }

    @Test
    void shouldBeAbleToGoToErp() {
        var result = erpClient.checkHealth();
        assertThatResult(result).isSuccess();
    }
}


