package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ApiE2eTest extends BaseE2eTest {

    @Override
    protected ShopDriver createDriver() {
        return DriverFactory.createShopApiDriver();
    }

    @Test
    void shouldRejectOrderWithNullQuantity() {
        var result = shopDriver.placeOrder("some-sku", null, "US");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Quantity must not be empty");
    }

    @Test
    void shouldRejectOrderWithNullSku() {
        var result = shopDriver.placeOrder(null, "5", "US");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("SKU must not be empty");
    }

    @Test
    void shouldRejectOrderWithNullCountry() {
        var result = shopDriver.placeOrder("some-sku", "5", null);
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Country must not be empty");
    }

    @Test
    void shouldNotCancelNonExistentOrder() {
        var result = shopDriver.cancelOrder("NON-EXISTENT-ORDER-99999");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Order NON-EXISTENT-ORDER-99999 does not exist.");
    }

    @Test
    void shouldNotCancelAlreadyCancelledOrder() {
        var sku = "MNO-" + UUID.randomUUID();
        erpApiDriver.createProduct(sku, "35.00");
        var placeOrderResult = shopDriver.placeOrder(sku, "3", "US");
        assertThat(placeOrderResult.isSuccess()).isTrue();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();

        // Cancel the order first time - should succeed
        var firstCancelResult = shopDriver.cancelOrder(orderNumber);
        assertThat(firstCancelResult.isSuccess()).isTrue();

        // Try to cancel the same order again - should fail
        var secondCancelResult = shopDriver.cancelOrder(orderNumber);
        assertThat(secondCancelResult.isFailure()).isTrue();
        assertThat(secondCancelResult.getErrors()).contains("Order has already been cancelled");
    }
}
