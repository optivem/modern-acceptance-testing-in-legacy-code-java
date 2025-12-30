package com.optivem.eshop.systemtest.core.shop.client.api.controllers;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.ViewOrderDetailsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.client.api.dtos.error.ProblemDetailResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Result;

public class OrderController {

    private static final String ENDPOINT = "/api/orders";

    private final JsonHttpClient<ProblemDetailResponse> httpClient;

    public OrderController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
    }

    public Result<PlaceOrderResponse, ProblemDetailResponse> placeOrder(PlaceOrderRequest request) {
        return httpClient.post(ENDPOINT, request, PlaceOrderResponse.class);
    }

    public Result<ViewOrderDetailsResponse, ProblemDetailResponse> viewOrder(String orderNumber) {
        return httpClient.get(ENDPOINT + "/" + orderNumber, ViewOrderDetailsResponse.class);
    }

    public Result<Void, ProblemDetailResponse> cancelOrder(String orderNumber) {
        return httpClient.post(ENDPOINT + "/" + orderNumber + "/cancel");
    }
}

