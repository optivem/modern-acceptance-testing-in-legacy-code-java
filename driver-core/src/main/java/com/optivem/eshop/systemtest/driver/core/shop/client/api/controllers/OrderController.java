package com.optivem.eshop.systemtest.driver.core.shop.client.api.controllers;

import com.optivem.eshop.systemtest.driver.api.shop.dtos.orders.ViewOrderResponse;
import com.optivem.eshop.systemtest.driver.api.shop.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.driver.api.shop.dtos.orders.PlaceOrderResponse;
import com.optivem.eshop.systemtest.driver.core.shop.client.api.dtos.errors.ProblemDetailResponse;
import com.optivem.eshop.systemtest.driver.core.shared.http.JsonHttpClient;
import com.optivem.commons.util.Result;

public class OrderController {

    private static final String ENDPOINT = "/api/orders";

    private final JsonHttpClient<ProblemDetailResponse> httpClient;

    public OrderController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
    }

    public Result<PlaceOrderResponse, ProblemDetailResponse> placeOrder(PlaceOrderRequest request) {
        return httpClient.post(ENDPOINT, request, PlaceOrderResponse.class);
    }

    public Result<ViewOrderResponse, ProblemDetailResponse> viewOrder(String orderNumber) {
        return httpClient.get(ENDPOINT + "/" + orderNumber, ViewOrderResponse.class);
    }

    public Result<Void, ProblemDetailResponse> cancelOrder(String orderNumber) {
        return httpClient.post(ENDPOINT + "/" + orderNumber + "/cancel");
    }
}


