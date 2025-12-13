package com.optivem.eshop.systemtest.core.shop.driver.api.client.controllers;

import com.optivem.http.HttpGateway;
import com.optivem.http.HttpUtils;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.requests.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.PlaceOrderResponse;
import com.optivem.lang.Result;

public class OrderController {

    private static final String ENDPOINT = "/api/orders";

    private final HttpGateway httpClient;

    public OrderController(HttpGateway httpClient) {
        this.httpClient = httpClient;
    }

    public Result<PlaceOrderResponse> placeOrder(PlaceOrderRequest request) {
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

