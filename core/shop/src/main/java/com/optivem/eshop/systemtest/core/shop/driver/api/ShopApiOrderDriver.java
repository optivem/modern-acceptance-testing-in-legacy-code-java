package com.optivem.eshop.systemtest.core.shop.driver.api;

import com.optivem.eshop.systemtest.core.shop.client.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.ViewOrderDetailsResponse;
import com.optivem.eshop.systemtest.core.shop.driver.OrderDriver;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.error.SystemError;
import com.optivem.lang.Result;

public class ShopApiOrderDriver implements OrderDriver {
    private final ShopApiClient apiClient;

    public ShopApiOrderDriver(ShopApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Result<PlaceOrderResponse, SystemError> placeOrder(PlaceOrderRequest request) {
        return apiClient.orders().placeOrder(request).mapError(SystemError::from);
    }

    @Override
    public Result<Void, SystemError> cancelOrder(String orderNumber) {
        return apiClient.orders().cancelOrder(orderNumber).mapError(SystemError::from);
    }

    @Override
    public Result<ViewOrderDetailsResponse, SystemError> viewOrder(String orderNumber) {
        return apiClient.orders().viewOrder(orderNumber).mapError(SystemError::from);
    }
}

