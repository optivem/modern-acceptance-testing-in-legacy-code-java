package com.optivem.eshop.systemtest.core.drivers.system.shop.api;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.Closer;
import com.optivem.eshop.systemtest.core.drivers.commons.clients.HttpGateway;
import com.optivem.eshop.systemtest.core.drivers.system.shop.api.client.ShopApiClient;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.drivers.commons.Result;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;

import java.net.http.HttpClient;

public class ShopApiDriver implements ShopDriver {
    private final HttpClient httpClient;
    private final ShopApiClient apiClient;

    public ShopApiDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var testHttpClient = new HttpGateway(httpClient, baseUrl);
        this.apiClient = new ShopApiClient(testHttpClient);
    }

    @Override
    public Result<Void> goToShop() {
        return apiClient.health().checkHealth();
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
        Closer.close(httpClient);
    }
}

