package com.optivem.eshop.systemtest.driver.adapter.shop.api.client;

import com.optivem.eshop.systemtest.driver.adapter.shop.api.client.controllers.CouponController;
import com.optivem.eshop.systemtest.driver.adapter.shop.api.client.controllers.HealthController;
import com.optivem.eshop.systemtest.driver.adapter.shop.api.client.controllers.OrderController;
import com.optivem.eshop.systemtest.driver.adapter.shop.api.client.controllers.ReviewController;
import com.optivem.eshop.systemtest.driver.adapter.shop.api.client.dtos.errors.ProblemDetailResponse;
import com.optivem.eshop.systemtest.driver.adapter.shared.client.http.JsonHttpClient;
import com.optivem.common.Closer;

public class ShopApiClient implements AutoCloseable {
    private final JsonHttpClient<ProblemDetailResponse> httpClient;
    private final HealthController healthController;
    private final OrderController orderController;
    private final CouponController couponController;
    private final ReviewController reviewController;

    public ShopApiClient(String baseUrl) {
        this.httpClient = new JsonHttpClient<>(baseUrl, ProblemDetailResponse.class);
        this.healthController = new HealthController(httpClient);
        this.orderController = new OrderController(httpClient);
        this.couponController = new CouponController(httpClient);
        this.reviewController = new ReviewController(httpClient);
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

    public ReviewController reviews() {
        return reviewController;
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}



