package com.optivem.eshop.systemtest.driver.core.shop.driver.api;

import com.optivem.eshop.systemtest.driver.core.shop.client.api.ShopApiClient;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.coupons.PublishCouponRequest;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.errors.SystemError;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.orders.PlaceOrderResponse;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.orders.ViewOrderResponse;
import com.optivem.eshop.systemtest.driver.api.shop.driver.ShopDriver;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;

public class ShopApiDriver implements ShopDriver {
    private final ShopApiClient apiClient;

    public ShopApiDriver(String baseUrl) {
        this.apiClient = new ShopApiClient(baseUrl);
    }

    @Override
    public void close() {
        Closer.close(apiClient);
    }

    @Override
    public Result<Void, SystemError> goToShop() {
        return apiClient.health().checkHealth().mapError(SystemErrorMapper::from);
    }

    @Override
    public Result<PlaceOrderResponse, SystemError> placeOrder(PlaceOrderRequest request) {
        return apiClient.orders().placeOrder(request).mapError(SystemErrorMapper::from);
    }

    @Override
    public Result<Void, SystemError> cancelOrder(String orderNumber) {
        return apiClient.orders().cancelOrder(orderNumber).mapError(SystemErrorMapper::from);
    }

    @Override
    public Result<ViewOrderResponse, SystemError> viewOrder(String orderNumber) {
        return apiClient.orders().viewOrder(orderNumber).mapError(SystemErrorMapper::from);
    }

    @Override
    public Result<Void, SystemError> publishCoupon(PublishCouponRequest request) {
        return apiClient.coupons().publishCoupon(request).mapError(SystemErrorMapper::from);
    }

    @Override
    public Result<BrowseCouponsResponse, SystemError> browseCoupons() {
        return apiClient.coupons().browseCoupons().mapError(SystemErrorMapper::from);
    }
}

