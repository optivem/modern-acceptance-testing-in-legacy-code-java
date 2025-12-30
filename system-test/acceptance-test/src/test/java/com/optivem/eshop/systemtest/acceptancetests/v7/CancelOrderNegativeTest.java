package com.optivem.eshop.systemtest.acceptancetests.v7;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.enums.OrderStatus;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.DataSource;
import org.junit.jupiter.api.TestTemplate;

public class CancelOrderNegativeTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.API})
    @DataSource({"NON-EXISTENT-ORDER-99999", "Order NON-EXISTENT-ORDER-99999 does not exist."})
    @DataSource({"NON-EXISTENT-ORDER-88888", "Order NON-EXISTENT-ORDER-88888 does not exist."})
    @DataSource({"NON-EXISTENT-ORDER-77777", "Order NON-EXISTENT-ORDER-77777 does not exist."})
    void shouldNotCancelNonExistentOrder(String orderNumber, String expectedErrorMessage) {
        scenario
                .when().cancelOrder().withOrderNumber(orderNumber)
                .then().shouldFail().errorMessage(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldNotCancelAlreadyCancelledOrder() {
        scenario
                .given().order().withStatus(OrderStatus.CANCELLED)
                .when().cancelOrder()
                .then().shouldFail().errorMessage("Order has already been cancelled");
    }

    // TODO: should not be able to cancel order on 31st Dec

}

