package com.optivem.eshop.systemtest.core.clients.system.api.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.commons.TestHttpUtils;
import com.optivem.eshop.systemtest.core.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.commons.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.drivers.system.Result;

public class OrderController {

    private static final String ENDPOINT = "/orders";

    private final TestHttpClient httpClient;

    public OrderController(TestHttpClient httpClient) {
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

