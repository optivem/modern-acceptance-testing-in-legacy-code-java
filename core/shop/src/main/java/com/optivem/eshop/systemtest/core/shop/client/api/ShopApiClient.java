package com.optivem.eshop.systemtest.core.shop.client.api;

import com.optivem.eshop.systemtest.core.shop.client.api.controllers.HealthController;
import com.optivem.eshop.systemtest.core.shop.client.api.controllers.OrderController;
import com.optivem.eshop.systemtest.core.shop.client.api.dtos.error.ProblemDetailResponse;
import com.optivem.http.JsonHttpClient;

public class ShopApiClient {

    private final HealthController healthController;
    private final OrderController orderController;

    public ShopApiClient(JsonHttpClient<ProblemDetailResponse> httpGateway) {
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

