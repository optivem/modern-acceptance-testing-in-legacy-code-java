package com.optivem.eshop.systemtest.e2etests.v2;

import com.optivem.eshop.systemtest.base.v2.BaseClientTest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.commons.util.ResultAssert.assertThatResult;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.COUNTRY;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.SKU;
import static org.assertj.core.api.Assertions.assertThat;

class PlaceOrderNegativeApiTest extends BaseClientTest {

    @BeforeEach
    void setUp() {
        setUpShopApiClient();
        setUpExternalClients();
    }

    @Test
    void shouldNotPlaceOrderWhenQuantityIsZero() {
        // Given
        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku(SKU)
                .quantity("0")
                .country(COUNTRY)
                .build();

        // When
        var placeOrderResult = shopApiClient.orders().placeOrder(placeOrderRequest);

        // Then
        assertThatResult(placeOrderResult).isFailure();
        var error = placeOrderResult.getError();
        assertThat(error.getDetail()).contains("quantity");
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
        var placeOrderResult = shopApiClient.orders().placeOrder(placeOrderRequest);

        // Then
        assertThatResult(placeOrderResult).isFailure();
        var error = placeOrderResult.getError();
        assertThat(error.getDetail()).contains("SKU");
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
        var placeOrderResult = shopApiClient.orders().placeOrder(placeOrderRequest);

        // Then
        assertThatResult(placeOrderResult).isFailure();
        var error = placeOrderResult.getError();
        assertThat(error.getDetail()).contains("SKU");
    }
}
