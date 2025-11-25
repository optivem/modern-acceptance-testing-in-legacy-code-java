package com.optivem.eshop.systemtest.core.clients.system.api.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.commons.dtos.PlaceOrderRequest;

import java.net.http.HttpResponse;

public class OrderController {

    private static final String ENDPOINT = "/orders";

    private final TestHttpClient httpClient;

    public OrderController(TestHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse<String> placeOrder(String sku, String quantity, String country) {
        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .build();

        return httpClient.post(ENDPOINT, request);
    }

    public HttpResponse<String> viewOrder(String orderNumber) {
        return httpClient.get(ENDPOINT + "/" + orderNumber);
    }

    public HttpResponse<String> cancelOrder(String orderNumber) {
        return httpClient.post(ENDPOINT + "/" + orderNumber + "/cancel");
    }
}

