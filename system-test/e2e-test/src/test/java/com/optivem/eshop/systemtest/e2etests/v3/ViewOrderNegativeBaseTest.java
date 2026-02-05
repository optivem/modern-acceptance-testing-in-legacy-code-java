package com.optivem.eshop.systemtest.e2etests.v3;

import com.optivem.eshop.systemtest.e2etests.v3.base.BaseE2eTest;
import org.junit.jupiter.api.Test;

import static com.optivem.commons.util.ResultAssert.assertThatResult;

abstract class ViewOrderNegativeBaseTest extends BaseE2eTest {

    @Test
    void shouldNotViewOrderWhenOrderNumberDoesNotExist() {
        // Given
        var orderNumber = "ORD-99999999";

        // When
        var viewOrderResult = shopDriver.orders().viewOrder(orderNumber);

        // Then
        assertThatResult(viewOrderResult).isValidationFailure("Order number does not exist");
    }
}
