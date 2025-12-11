package com.optivem.eshop.systemtest.dsl.shop.drivers.api.client;

import com.optivem.http.HttpGateway;
import com.optivem.eshop.systemtest.dsl.shop.drivers.api.client.controllers.HealthController;
import com.optivem.eshop.systemtest.dsl.shop.drivers.api.client.controllers.OrderController;

public class ShopApiClient {

    private final HealthController healthController;
    private final OrderController orderController;

    public ShopApiClient(HttpGateway httpGateway) {
        this.healthController = new HealthController(httpGateway);
        this.orderController = new OrderController(httpGateway);
    }

    public HealthController health() {
        return healthController;
    }

    public OrderController orders() {
        return orderController;
    }
}

