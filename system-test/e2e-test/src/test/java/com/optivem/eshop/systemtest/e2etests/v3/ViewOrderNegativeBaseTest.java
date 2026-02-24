package com.optivem.eshop.systemtest.e2etests.v3;

import com.optivem.eshop.systemtest.e2etests.v3.base.BaseE2eTest;
import org.junit.jupiter.api.Test;

import static com.optivem.commons.util.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;

abstract class ViewOrderNegativeBaseTest extends BaseE2eTest {

    @Test
    void shouldNotBeAbleToViewNonExistentOrder() {
        var orderNumber = "NON-EXISTENT-ORDER-99999";

        var result = shopDriver.viewOrder(orderNumber);

        assertThatResult(result).isFailure();
        var error = result.getError();
        assertThat(error.getMessage()).isEqualTo("Order NON-EXISTENT-ORDER-99999 does not exist.");
    }
}

