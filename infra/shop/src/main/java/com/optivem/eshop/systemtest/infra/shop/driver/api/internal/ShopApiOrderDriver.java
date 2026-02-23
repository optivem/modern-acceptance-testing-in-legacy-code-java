package com.optivem.eshop.systemtest.infra.shop.driver.api.internal;

import com.optivem.eshop.systemtest.infra.shop.client.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.ViewOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.internal.OrderDriver;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.eshop.systemtest.infra.shop.driver.api.SystemErrorMapper;
import com.optivem.commons.util.Result;

public class ShopApiOrderDriver implements OrderDriver {
    private final ShopApiClient apiClient;

    public ShopApiOrderDriver(ShopApiClient apiClient) {
        this.apiClient = apiClient;
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
}

