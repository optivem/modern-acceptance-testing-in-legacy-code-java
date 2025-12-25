package com.optivem.eshop.systemtest.smoketests.v4;

import com.optivem.eshop.systemtest.base.v4.BaseChannelDriverTest;
import org.junit.jupiter.api.Test;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class ErpSmokeTest extends BaseChannelDriverTest {
    @Test
    void shouldBeAbleToGoToErp() {
        var result = erpDriver.goToErp();
        assertThatResult(result).isSuccess();
    }
}

