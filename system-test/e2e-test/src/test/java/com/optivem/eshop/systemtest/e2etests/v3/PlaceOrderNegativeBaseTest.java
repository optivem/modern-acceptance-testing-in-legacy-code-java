package com.optivem.eshop.systemtest.e2etests.v3;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.e2etests.v3.base.BaseE2eTest;
import org.junit.jupiter.api.Test;

import static com.optivem.commons.util.ResultAssert.assertThatResult;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.COUNTRY;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.SKU;

abstract class PlaceOrderNegativeBaseTest extends BaseE2eTest {

    @Test
    void shouldNotPlaceOrderWhenQuantityIsZero() {
        // Given
        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku(SKU)
                .quantity("0")
                .country(COUNTRY)
                .build();

        // When
        var placeOrderResult = shopDriver.orders().placeOrder(placeOrderRequest);

        // Then
        assertThatResult(placeOrderResult).isValidationFailure("Order quantity must be greater than zero");
    }

    @Test
    void shouldNotPlaceOrderWhenSKUDoesNotExist() {
        // Given
        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku("INVALID-SKU")
                .quantity("5")
                .country(COUNTRY)
                .build();

        // When
        var placeOrderResult = shopDriver.orders().placeOrder(placeOrderRequest);

        // Then
        assertThatResult(placeOrderResult).isValidationFailure("Product SKU does not exist");
    }

    @Test
    void shouldNotPlaceOrderWhenSKUIsMissing() {
        // Given
        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku("")
                .quantity("5")
                .country(COUNTRY)
                .build();

        // When
        var placeOrderResult = shopDriver.orders().placeOrder(placeOrderRequest);

        // Then
        assertThatResult(placeOrderResult).isValidationFailure("SKU is required");
    }
}
