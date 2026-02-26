package com.optivem.eshop.systemtest.driver.core.shop.client.api;

import com.optivem.eshop.systemtest.driver.core.shop.client.api.controllers.CouponController;
import com.optivem.eshop.systemtest.driver.core.shop.client.api.controllers.HealthController;
import com.optivem.eshop.systemtest.driver.core.shop.client.api.controllers.OrderController;
import com.optivem.eshop.systemtest.driver.core.shop.client.api.dtos.errors.ProblemDetailResponse;
import com.optivem.eshop.systemtest.driver.core.shared.http.JsonHttpClient;
import com.optivem.common.Closer;

public class ShopApiClient implements AutoCloseable {
    private final JsonHttpClient<ProblemDetailResponse> httpClient;
    private final HealthController healthController;
    private final OrderController orderController;
    private final CouponController couponController;

    public ShopApiClient(String baseUrl) {
        this.httpClient = new JsonHttpClient<>(baseUrl, ProblemDetailResponse.class);
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

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}


