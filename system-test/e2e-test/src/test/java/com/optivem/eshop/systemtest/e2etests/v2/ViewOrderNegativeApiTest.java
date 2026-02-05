package com.optivem.eshop.systemtest.e2etests.v2;

import com.optivem.eshop.systemtest.base.v2.BaseClientTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.optivem.commons.util.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled("V2 tests disabled for now")
class ViewOrderNegativeApiTest extends BaseClientTest {

    @BeforeEach
    void setUp() {
        setUpShopApiClient();
        setUpExternalClients();
    }

    @Test
    void shouldNotViewOrderWhenOrderNumberDoesNotExist() {
        // Given
        var orderNumber = "ORD-99999999";

        // When
        var viewOrderResult = shopApiClient.orders().viewOrder(orderNumber);

        // Then
        assertThatResult(viewOrderResult).isFailure();
        var error = viewOrderResult.getError();
        assertThat(error.getDetail()).isEqualTo("Order ORD-99999999 does not exist.");
    }
}
