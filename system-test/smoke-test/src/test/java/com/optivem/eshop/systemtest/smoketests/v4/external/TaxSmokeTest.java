package com.optivem.eshop.systemtest.smoketests.v4.external;

import com.optivem.eshop.systemtest.base.v4.BaseChannelDriverTest;
import org.junit.jupiter.api.Test;

import static com.optivem.commons.util.ResultAssert.assertThatResult;

public class TaxSmokeTest extends BaseChannelDriverTest {
    @Test
    void shouldBeAbleToGoToTax() {
        var result = taxDriver.goToTax();
        assertThatResult(result).isSuccess();
    }
}

