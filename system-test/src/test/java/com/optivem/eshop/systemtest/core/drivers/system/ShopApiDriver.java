package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.clients.system.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.commons.dtos.PlaceOrderResponse;

public class ShopApiDriver implements ShopDriver {
    private final ShopApiClient apiClient;

    public ShopApiDriver(String baseUrl) {
        this.apiClient = new ShopApiClient(baseUrl);
    }

    @Override
    public Result<Void> goToShop() {
        return apiClient.echo().echo();
    }

    @Override
    public Result<PlaceOrderResponse> placeOrder(String sku, String quantity, String country) {
        return apiClient.orders().placeOrder(sku, quantity, country);
    }

    @Override
    public Result<Void> cancelOrder(String orderNumber) {
        return apiClient.orders().cancelOrder(orderNumber);
    }

    @Override
    public Result<GetOrderResponse> viewOrder(String orderNumber) {
        return apiClient.orders().viewOrder(orderNumber);
    }

    @Override
    public void close() {
        apiClient.close();
    }
}

