package com.optivem.eshop.systemtest.e2etests.v2;

import com.optivem.eshop.systemtest.e2etests.v2.base.BaseE2eTest;
import org.junit.jupiter.api.Test;

import static com.optivem.common.util.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;

class ViewOrderNegativeApiTest extends BaseE2eTest {
    @Override
    protected void setShopDriver() {
        setUpShopApiClient();
    }

    @Test
    void shouldNotBeAbleToViewNonExistentOrder() {
        var orderNumber = "NON-EXISTENT-ORDER-99999";

        var result = shopApiClient.orders().viewOrder(orderNumber);

        assertThatResult(result).isFailure();
        var error = result.getError();
        assertThat(error.getDetail()).isEqualTo("Order NON-EXISTENT-ORDER-99999 does not exist.");
    }
}

