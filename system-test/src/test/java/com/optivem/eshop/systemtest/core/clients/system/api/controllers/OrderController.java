package com.optivem.eshop.systemtest.core.clients.system.api.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.BaseController;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.PlaceOrderResponse;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class OrderController extends BaseController {

    public static final String CONTROLLER_ENDPOINT = "api/orders";

    public OrderController(HttpClient client, String baseUrl) {
        super(client, baseUrl);
    }

    public HttpResponse<String> placeOrder(String sku, String quantity, String country) {
        var request = new PlaceOrderRequest();
        request.setSku(sku);
        request.setQuantity(quantity);
        request.setCountry(country);

        return post(CONTROLLER_ENDPOINT, request);
    }

    public PlaceOrderResponse assertOrderPlacedSuccessfully(HttpResponse<String> httpResponse) {
        assertCreated(httpResponse);
        return readBody(httpResponse, PlaceOrderResponse.class);
    }

    public void assertOrderPlacementFailed(HttpResponse<String> httpResponse) {
        assertUnprocessableEntity(httpResponse);
    }

    public String getErrorMessage(HttpResponse<String> httpResponse) {
        return httpResponse.body();
    }

    public HttpResponse<String> viewOrder(String orderNumber) {
        var endpoint = CONTROLLER_ENDPOINT + "/" + orderNumber;
        return get(endpoint);
    }

    public GetOrderResponse assertOrderViewedSuccessfully(HttpResponse<String> httpResponse) {
        assertOk(httpResponse);
        return readBody(httpResponse, GetOrderResponse.class);
    }

    public HttpResponse<String> cancelOrder(String orderNumber) {
        var endpoint = CONTROLLER_ENDPOINT + "/" + orderNumber + "/cancel";
        return post(endpoint);
    }

    public void assertOrderCancelledSuccessfully(HttpResponse<String> httpResponse) {
        assertNoContent(httpResponse);
    }
}

