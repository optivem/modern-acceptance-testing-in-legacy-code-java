package com.optivem.eshop.systemtest.core.clients.system.api;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.system.api.controllers.EchoController;
import com.optivem.eshop.systemtest.core.clients.system.api.controllers.OrderController;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.ErrorResponse;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.PlaceOrderResponse;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class ShopApiClient implements AutoCloseable {

    private final HttpClient httpClient;
    private final TestHttpClient testHttpClient;
    private final EchoController echoController;
    private final OrderController orderController;

    public ShopApiClient(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.testHttpClient = new TestHttpClient(httpClient, baseUrl);
        this.echoController = new EchoController(testHttpClient);
        this.orderController = new OrderController(testHttpClient);
    }

    public EchoController echo() {
        return echoController;
    }

    public OrderController orders() {
        return orderController;
    }

    public String getErrorMessage(HttpResponse<String> httpResponse) {
        var response = testHttpClient.readBody(httpResponse, ErrorResponse.class);
        return response.getMessage();
    }

    @Override
    public void close() {
        httpClient.close();
    }
}

