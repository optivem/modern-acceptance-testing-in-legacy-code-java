package com.optivem.eshop.systemtest.core.shop.driver.api;

import com.optivem.eshop.systemtest.core.shop.client.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.eshop.systemtest.core.shop.driver.internal.CouponDriver;
import com.optivem.eshop.systemtest.core.shop.driver.internal.OrderDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;
import com.optivem.eshop.systemtest.core.shop.driver.api.internal.ShopApiCouponDriver;
import com.optivem.eshop.systemtest.core.shop.driver.api.internal.ShopApiOrderDriver;

public class ShopApiDriver implements ShopDriver {
    private final ShopApiClient apiClient;
    private final OrderDriver orderDriver;
    private final CouponDriver couponDriver;

    public ShopApiDriver(String baseUrl) {
        this.apiClient = new ShopApiClient(baseUrl);
        this.orderDriver = new ShopApiOrderDriver(apiClient);
        this.couponDriver = new ShopApiCouponDriver(apiClient);
    }

    @Override
    public void close() {
        Closer.close(apiClient);
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
}

