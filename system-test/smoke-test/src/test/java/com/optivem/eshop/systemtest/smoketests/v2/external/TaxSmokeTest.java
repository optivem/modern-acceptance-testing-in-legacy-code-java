package com.optivem.eshop.systemtest.smoketests.v2.external;

import com.optivem.eshop.systemtest.base.v2.BaseClientTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.commons.util.ResultAssert.assertThatResult;

public class TaxSmokeTest extends BaseClientTest {
    @BeforeEach
    void setUp() {
        setUpExternalClients();
    }

    @Test
    void shouldBeAbleToGoToTax() {
        var result = taxClient.checkHealth();
        assertThatResult(result).isSuccess();
    }
}

