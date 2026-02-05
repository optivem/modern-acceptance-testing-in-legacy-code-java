package com.optivem.eshop.systemtest.e2etests.v1;

import com.optivem.eshop.systemtest.base.v1.BaseRawTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.COUNTRY;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.SKU;
import static org.assertj.core.api.Assertions.assertThat;

class PlaceOrderNegativeApiTest extends BaseRawTest {

    @BeforeEach
    void setUp() {
        setUpShopHttpClient();
        setUpExternalHttpClients();
    }

    @Test
    void shouldNotPlaceOrderWhenQuantityIsZero() throws Exception {
        // Given
        var placeOrderJson = """
                {
                    "sku": "%s",
                    "quantity": "0",
                    "country": "%s"
                }
                """.formatted(SKU, COUNTRY);

        // When
        var placeOrderUri = URI.create(getShopApiBaseUrl() + "/api/orders");
        var placeOrderRequest = HttpRequest.newBuilder()
                .uri(placeOrderUri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(placeOrderJson))
                .build();

        var placeOrderResponse = shopApiHttpClient.send(placeOrderRequest, HttpResponse.BodyHandlers.ofString());

        // Then
        assertThat(placeOrderResponse.statusCode()).isEqualTo(400);

        var errorBody = httpObjectMapper.readTree(placeOrderResponse.body());
        assertThat(errorBody.get("detail").asText()).isEqualTo("The request contains one or more validation errors");
        
        var errors = errorBody.get("errors");
        assertThat(errors).isNotNull();
        assertThat(errors.isArray()).isTrue();
        
        boolean foundQuantityError = false;
        for (var error : errors) {
            if (error.get("field").asText().equals("quantity") && 
                error.get("message").asText().equals("Quantity must be positive")) {
                foundQuantityError = true;
                break;
            }
        }
        assertThat(foundQuantityError).isTrue();
    }

    @Test
    void shouldNotPlaceOrderWhenSKUDoesNotExist() throws Exception {
        // Given
        var placeOrderJson = """
                {
                    "sku": "INVALID-SKU",
                    "quantity": "5",
                    "country": "%s"
                }
                """.formatted(COUNTRY);

        // When
        var placeOrderUri = URI.create(getShopApiBaseUrl() + "/api/orders");
        var placeOrderRequest = HttpRequest.newBuilder()
                .uri(placeOrderUri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(placeOrderJson))
                .build();

        var placeOrderResponse = shopApiHttpClient.send(placeOrderRequest, HttpResponse.BodyHandlers.ofString());

        // Then
        assertThat(placeOrderResponse.statusCode()).isEqualTo(400);

        var errorBody = httpObjectMapper.readTree(placeOrderResponse.body());
        assertThat(errorBody.get("detail").asText()).isEqualTo("The request contains one or more validation errors");
        
        var errors = errorBody.get("errors");
        assertThat(errors).isNotNull();
        assertThat(errors.isArray()).isTrue();
        
        boolean foundSkuError = false;
        for (var error : errors) {
            if (error.get("field").asText().equals("sku") && 
                error.get("message").asText().equals("Product does not exist for SKU: INVALID-SKU")) {
                foundSkuError = true;
                break;
            }
        }
        assertThat(foundSkuError).isTrue();
    }

    @Test
    void shouldNotPlaceOrderWhenSKUIsMissing() throws Exception {
        // Given
        var placeOrderJson = """
                {
                    "sku": "",
                    "quantity": "5",
                    "country": "%s"
                }
                """.formatted(COUNTRY);

        // When
        var placeOrderUri = URI.create(getShopApiBaseUrl() + "/api/orders");
        var placeOrderRequest = HttpRequest.newBuilder()
                .uri(placeOrderUri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(placeOrderJson))
                .build();

        var placeOrderResponse = shopApiHttpClient.send(placeOrderRequest, HttpResponse.BodyHandlers.ofString());

        // Then
        assertThat(placeOrderResponse.statusCode()).isEqualTo(400);

        var errorBody = httpObjectMapper.readTree(placeOrderResponse.body());
        assertThat(errorBody.get("detail").asText()).isEqualTo("The request contains one or more validation errors");
        
        var errors = errorBody.get("errors");
        assertThat(errors).isNotNull();
        assertThat(errors.isArray()).isTrue();
        
        boolean foundSkuError = false;
        for (var error : errors) {
            if (error.get("field").asText().equals("sku") && 
                error.get("message").asText().equals("SKU must not be empty")) {
                foundSkuError = true;
                break;
            }
        }
        assertThat(foundSkuError).isTrue();
    }
}
