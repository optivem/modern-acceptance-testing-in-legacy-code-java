package com.optivem.eshop.systemtest.acceptancetests.v7.orders;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.test.Time;
import com.optivem.test.Channel;
import com.optivem.test.DataSource;

import org.junit.jupiter.api.Disabled;
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

    @TestTemplate
    @Channel({ChannelType.API})
    void cannotCancelNonExistentOrder() {
        scenario
                .when().cancelOrder().withOrderNumber("non-existent-order-12345")
                .then().shouldFail().errorMessage("Order non-existent-order-12345 does not exist.");
    }

    @Disabled
    @Time
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"2024-12-31T22:00:00Z"})   // Start of blackout period
    @DataSource({"2026-12-31T22:00:01Z"})   // Just after start
    @DataSource({"2025-12-31T22:15:00Z"})   // Middle of blackout period
    @DataSource({"2028-12-31T22:29:59Z"})   // Just before end
    @DataSource({"2021-12-31T22:30:00Z"})   // End of blackout period
    void cannotCancelAnOrderOn31stDecBetween2200And2230(String timeIso) {
        scenario
                .given().clock().withTime(timeIso)
                .and().order().withStatus(OrderStatus.PLACED)
                .when().cancelOrder()
                .then().shouldFail().errorMessage("Order cancellation is not allowed on December 31st between 22:00 and 23:00")
                .and().order().hasStatus(OrderStatus.PLACED);
    }
}

