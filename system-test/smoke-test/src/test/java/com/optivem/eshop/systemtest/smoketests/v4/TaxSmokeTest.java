package com.optivem.eshop.systemtest.smoketests.v4;

import com.optivem.eshop.systemtest.base.v4.BaseChannelDriverTest;
import org.junit.jupiter.api.Test;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class TaxSmokeTest extends BaseChannelDriverTest {
    @Test
    void shouldBeAbleToGoToTax() {
        var result = taxDriver.goToTax();
        assertThatResult(result).isSuccess();
    }
}

