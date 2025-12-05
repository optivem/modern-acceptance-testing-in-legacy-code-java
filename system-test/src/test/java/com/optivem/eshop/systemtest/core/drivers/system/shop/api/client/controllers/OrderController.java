package com.optivem.eshop.systemtest.core.drivers.system.shop.api.client.controllers;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.HttpGateway;
import com.optivem.eshop.systemtest.core.drivers.commons.clients.TestHttpUtils;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.drivers.commons.Result;

public class OrderController {

    private static final String ENDPOINT = "/api/orders";

    private final HttpGateway httpClient;

    public OrderController(HttpGateway httpClient) {
        this.httpClient = httpClient;
    }

    public Result<PlaceOrderResponse> placeOrder(String sku, String quantity, String country) {
        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .build();

        var httpResponse = httpClient.post(ENDPOINT, request);
        return TestHttpUtils.getCreatedResultOrFailure(httpResponse, PlaceOrderResponse.class);
    }

    public Result<GetOrderResponse> viewOrder(String orderNumber) {
        var httpResponse = httpClient.get(ENDPOINT + "/" + orderNumber);
        return TestHttpUtils.getOkResultOrFailure(httpResponse, GetOrderResponse.class);
    }

    public Result<Void> cancelOrder(String orderNumber) {
        var httpResponse = httpClient.post(ENDPOINT + "/" + orderNumber + "/cancel");
        return TestHttpUtils.getNoContentResultOrFailure(httpResponse);
    }
}

