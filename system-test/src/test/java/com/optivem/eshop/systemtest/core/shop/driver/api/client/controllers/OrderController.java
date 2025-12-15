package com.optivem.eshop.systemtest.core.shop.driver.api.client.controllers;

import com.optivem.http.JsonHttpClient;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.requests.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.PlaceOrderResponse;
import com.optivem.lang.Error;
import com.optivem.lang.Result;

public class OrderController {

    private static final String ENDPOINT = "/api/orders";

    private final JsonHttpClient httpClient;

    public OrderController(JsonHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<PlaceOrderResponse, Error> placeOrder(PlaceOrderRequest request) {
        return httpClient.post(ENDPOINT, request, PlaceOrderResponse.class)
                .mapFailure(JsonHttpClient::convertProblemDetailToError);
    }

    public Result<GetOrderResponse, Error> viewOrder(String orderNumber) {
        return httpClient.get(ENDPOINT + "/" + orderNumber, GetOrderResponse.class)
                .mapFailure(JsonHttpClient::convertProblemDetailToError);
    }

    public Result<Void, Error> cancelOrder(String orderNumber) {
        return httpClient.post(ENDPOINT + "/" + orderNumber + "/cancel", Void.class)
                .mapFailure(JsonHttpClient::convertProblemDetailToError);
    }
}

