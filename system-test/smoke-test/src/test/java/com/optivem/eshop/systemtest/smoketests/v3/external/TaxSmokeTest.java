package com.optivem.eshop.systemtest.smoketests.v3.external;

import com.optivem.eshop.systemtest.base.v3.BaseDriverTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.common.util.ResultAssert.assertThatResult;

class TaxSmokeTest extends BaseDriverTest {
    @BeforeEach
    void setUp() {
        setUpExternalDrivers();
    }

    @Test
    void shouldBeAbleToGoToTax() {
        var result = taxDriver.goToTax();
        assertThatResult(result).isSuccess();
    }
}


