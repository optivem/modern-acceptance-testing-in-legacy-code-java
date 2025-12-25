package com.optivem.eshop.systemtest.acceptancetests.v7;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.DataSource;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.ORDER_NUMBER;
import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.SKU;

public class CancelOrderNegativeTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.API})
    @DataSource({"NON-EXISTENT-ORDER-99999", "Order NON-EXISTENT-ORDER-99999 does not exist."})
    @DataSource({"NON-EXISTENT-ORDER-88888", "Order NON-EXISTENT-ORDER-88888 does not exist."})
    @DataSource({"NON-EXISTENT-ORDER-77777", "Order NON-EXISTENT-ORDER-77777 does not exist."})
    void shouldNotCancelNonExistentOrder(String orderNumber, String expectedErrorMessage) {
        scenario
                .given()
                .noProducts()
                .when()
                .cancelOrder()
                .withOrderNumber(orderNumber)
                .then()
                .shouldFail()
                .errorMessage(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldNotCancelAlreadyCancelledOrder() {
        scenario
                .given()
                .product()
                .withSku(SKU)
                .and().taxRate().withCountry("US").withTaxRate(0.0)
                .and().order()
                .withOrderNumber(ORDER_NUMBER)
                .withSku(SKU)
                .when()
                .cancelOrder()
                .withOrderNumber(ORDER_NUMBER)
                .then()
                .shouldSucceed();

        // Second cancellation should fail
        scenario
                .given()
                .noProducts()
                .when()
                .cancelOrder()
                .withOrderNumber(ORDER_NUMBER)
                .then()
                .shouldFail()
                .errorMessage("Order has already been cancelled");
    }
}

