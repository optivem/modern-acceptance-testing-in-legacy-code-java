package com.optivem.eshop.systemtest.core.shop.driver.api;

import com.optivem.eshop.systemtest.core.shop.client.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.shop.client.api.dtos.errors.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.shop.driver.CouponDriver;
import com.optivem.eshop.systemtest.core.shop.driver.OrderDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.commons.http.JsonHttpClient;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;

public class ShopApiDriver implements ShopDriver {
    private final JsonHttpClient<ProblemDetailResponse> httpClient;
    private final ShopApiClient apiClient;
    private final OrderDriver orderDriver;
    private final CouponDriver couponDriver;

    public ShopApiDriver(String baseUrl) {
        this.httpClient = new JsonHttpClient<>(baseUrl, ProblemDetailResponse.class);
        this.apiClient = new ShopApiClient(httpClient);
        this.orderDriver = new ShopApiOrderDriver(apiClient);
        this.couponDriver = new ShopApiCouponDriver(apiClient);
    }

    @Override
    public Result<Void, SystemError> goToShop() {
        return apiClient.health().checkHealth().mapError(SystemError::from);
    }

    @Override
    public OrderDriver orders() {
        return orderDriver;
    }

    @Override
    public CouponDriver coupons() {
        return couponDriver;
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}

