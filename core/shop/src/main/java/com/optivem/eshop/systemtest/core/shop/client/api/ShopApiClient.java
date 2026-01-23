package com.optivem.eshop.systemtest.core.shop.client.api;

import com.optivem.eshop.systemtest.core.shop.client.api.controllers.CouponController;
import com.optivem.eshop.systemtest.core.shop.client.api.controllers.HealthController;
import com.optivem.eshop.systemtest.core.shop.client.api.controllers.OrderController;
import com.optivem.eshop.systemtest.core.shop.client.api.dtos.errors.ProblemDetailResponse;
import com.optivem.commons.http.JsonHttpClient;

public class ShopApiClient {

    private final HealthController healthController;
    private final OrderController orderController;
    private final CouponController couponController;

    public ShopApiClient(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.healthController = new HealthController(httpClient);
        this.orderController = new OrderController(httpClient);
        this.couponController = new CouponController(httpClient);
    }

    public HealthController health() {
        return healthController;
    }

    public OrderController orders() {
        return orderController;
    }

    public CouponController coupons() {
        return couponController;
    }
}

