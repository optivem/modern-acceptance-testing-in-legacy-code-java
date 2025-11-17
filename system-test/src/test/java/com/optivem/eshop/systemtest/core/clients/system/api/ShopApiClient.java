package com.optivem.eshop.systemtest.core.clients.system.api;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.system.api.controllers.EchoController;
import com.optivem.eshop.systemtest.core.clients.system.api.controllers.OrderController;

import java.net.http.HttpClient;

public class ShopApiClient implements AutoCloseable {

    private final HttpClient client;
    private final TestHttpClient testHttpClient;
    private final EchoController echoController;
    private final OrderController orderController;

    public ShopApiClient(String baseUrl) {
        this.client = HttpClient.newHttpClient();
        this.testHttpClient = new TestHttpClient(client, baseUrl);
        this.echoController = new EchoController(testHttpClient);
        this.orderController = new OrderController(testHttpClient);
    }

    public EchoController echo() {
        return echoController;
    }

    public OrderController orders() {
        return orderController;
    }

    @Override
    public void close() {
        client.close();
    }
}

