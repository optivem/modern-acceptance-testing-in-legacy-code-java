package com.optivem.eshop.systemtest.core.clients.system.api;

import com.optivem.eshop.systemtest.core.clients.system.api.controllers.EchoController;
import com.optivem.eshop.systemtest.core.clients.system.api.controllers.OrderController;

import java.net.http.HttpClient;

public class ShopApiClient implements AutoCloseable {

    private final HttpClient client;
    private final EchoController echoController;
    private final OrderController orderController;

    public ShopApiClient(String baseUrl) {
        this.client = HttpClient.newHttpClient();
        this.echoController = new EchoController(client, baseUrl);
        this.orderController = new OrderController(client, baseUrl);
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

