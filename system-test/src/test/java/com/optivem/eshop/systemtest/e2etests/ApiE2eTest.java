package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import org.junit.jupiter.api.Test;

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
}
