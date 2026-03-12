package com.optivem.eshop.systemtest.v3.smoke.external;

import com.optivem.eshop.systemtest.v3.base.BaseDriverTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.eshop.dsl.common.ResultAssert.assertThatResult;

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


