package com.optivem.eshop.systemtest.v4.smoke.external;

import com.optivem.eshop.systemtest.v4.base.BaseChannelDriverTest;
import org.junit.jupiter.api.Test;

import static com.optivem.eshop.dsl.common.ResultAssert.assertThatResult;

class TaxSmokeTest extends BaseChannelDriverTest {
    @Test
    void shouldBeAbleToGoToTax() {
        var result = taxDriver.goToTax();
        assertThatResult(result).isSuccess();
    }
}


