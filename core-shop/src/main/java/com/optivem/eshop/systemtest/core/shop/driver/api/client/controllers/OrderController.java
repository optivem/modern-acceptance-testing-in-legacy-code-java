package com.optivem.eshop.systemtest.core.shop.driver.api.client.controllers;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailConverter;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.requests.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.PlaceOrderResponse;
import com.optivem.lang.Result;

public class OrderController {

    private static final String ENDPOINT = "/api/orders";

    private final JsonHttpClient<ProblemDetailResponse> httpClient;

    public OrderController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
    }

    public Result<PlaceOrderResponse, Error> placeOrder(PlaceOrderRequest request) {
        return httpClient.post(ENDPOINT, request, PlaceOrderResponse.class)
                .mapFailure(ProblemDetailConverter::toError);
    }

    public Result<GetOrderResponse, Error> viewOrder(String orderNumber) {
        return httpClient.get(ENDPOINT + "/" + orderNumber, GetOrderResponse.class)
                .mapFailure(ProblemDetailConverter::toError);
    }

    public Result<Void, Error> cancelOrder(String orderNumber) {
        return httpClient.post(ENDPOINT + "/" + orderNumber + "/cancel")
                .mapFailure(ProblemDetailConverter::toError);
    }
}

