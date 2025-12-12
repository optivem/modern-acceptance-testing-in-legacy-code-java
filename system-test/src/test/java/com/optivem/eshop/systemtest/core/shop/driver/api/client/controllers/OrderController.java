package com.optivem.eshop.systemtest.core.shop.driver.api.client.controllers;

import com.optivem.http.HttpGateway;
import com.optivem.http.HttpUtils;
import com.optivem.eshop.systemtest.core.shop.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.dtos.PlaceOrderResponse;
import com.optivem.results.Result;

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
        return HttpUtils.getCreatedResultOrFailure(httpResponse, PlaceOrderResponse.class);
    }

    public Result<GetOrderResponse> viewOrder(String orderNumber) {
        var httpResponse = httpClient.get(ENDPOINT + "/" + orderNumber);
        return HttpUtils.getOkResultOrFailure(httpResponse, GetOrderResponse.class);
    }

    public Result<Void> cancelOrder(String orderNumber) {
        var httpResponse = httpClient.post(ENDPOINT + "/" + orderNumber + "/cancel");
        return HttpUtils.getNoContentResultOrFailure(httpResponse);
    }
}

