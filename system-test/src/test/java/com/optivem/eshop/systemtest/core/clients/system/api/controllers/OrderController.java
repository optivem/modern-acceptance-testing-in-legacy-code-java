package com.optivem.eshop.systemtest.core.clients.system.api.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.PlaceOrderResponse;

import java.net.http.HttpResponse;

public class OrderController {

    private static final String ENDPOINT = "/orders";

    private final TestHttpClient httpClient;

    public OrderController(TestHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse<String> placeOrder(String sku, String quantity, String country) {
        var request = new PlaceOrderRequest();
        request.setSku(sku);
        request.setQuantity(quantity);
        request.setCountry(country);

        return httpClient.post(ENDPOINT, request);
    }

    public PlaceOrderResponse assertOrderPlacedSuccessfully(HttpResponse<String> httpResponse) {
        httpClient.assertCreated(httpResponse);
        return httpClient.readBody(httpResponse, PlaceOrderResponse.class);
    }

    public void assertOrderPlacementFailed(HttpResponse<String> httpResponse) {
        httpClient.assertUnprocessableEntity(httpResponse);
    }

    public String getErrorMessage(HttpResponse<String> httpResponse) {
        return httpResponse.body();
    }

    public HttpResponse<String> viewOrder(String orderNumber) {
        return httpClient.get(ENDPOINT + "/" + orderNumber);
    }

    public GetOrderResponse assertOrderViewedSuccessfully(HttpResponse<String> httpResponse) {
        httpClient.assertOk(httpResponse);
        return httpClient.readBody(httpResponse, GetOrderResponse.class);
    }

    public HttpResponse<String> cancelOrder(String orderNumber) {
        return httpClient.post(ENDPOINT + "/" + orderNumber + "/cancel");
    }

    public void assertOrderCancelledSuccessfully(HttpResponse<String> httpResponse) {
        httpClient.assertNoContent(httpResponse);
    }
}

