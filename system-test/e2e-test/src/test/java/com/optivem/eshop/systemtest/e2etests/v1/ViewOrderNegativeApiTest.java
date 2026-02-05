package com.optivem.eshop.systemtest.e2etests.v1;

import com.optivem.eshop.systemtest.base.v1.BaseRawTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("V1 tests disabled for now")
class ViewOrderNegativeApiTest extends BaseRawTest {

    @BeforeEach
    void setUp() {
        setUpShopHttpClient();
        setUpExternalHttpClients();
    }

    @Test
    void shouldNotViewOrderWhenOrderNumberDoesNotExist() throws Exception {
        // Given
        var orderNumber = "ORD-99999999";

        // When
        var viewOrderUri = URI.create(getShopApiBaseUrl() + "/api/orders/" + orderNumber);
        var viewOrderRequest = HttpRequest.newBuilder()
                .uri(viewOrderUri)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        var viewOrderResponse = shopApiHttpClient.send(viewOrderRequest, HttpResponse.BodyHandlers.ofString());

        // Then
        assertThat(viewOrderResponse.statusCode()).isEqualTo(404);

        var errorBody = httpObjectMapper.readTree(viewOrderResponse.body());
        assertThat(errorBody.get("detail").asText()).isEqualTo("Order ORD-99999999 does not exist.");
    }
}
