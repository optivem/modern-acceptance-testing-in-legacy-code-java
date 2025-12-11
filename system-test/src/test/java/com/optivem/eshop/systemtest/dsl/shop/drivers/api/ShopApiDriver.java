package com.optivem.eshop.systemtest.dsl.shop.drivers.api;

import com.optivem.eshop.systemtest.dsl.shop.dtos.PlaceOrderRequest;
import com.optivem.lang.Closer;
import com.optivem.http.HttpGateway;
import com.optivem.eshop.systemtest.dsl.shop.drivers.api.client.ShopApiClient;
import com.optivem.eshop.systemtest.dsl.shop.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.dsl.shop.dtos.PlaceOrderResponse;
import com.optivem.results.Result;
import com.optivem.eshop.systemtest.dsl.shop.drivers.ShopDriver;

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
    public Result<PlaceOrderResponse> placeOrder(PlaceOrderRequest request) {
        var sku = request.getSku();
        var quantity = request.getQuantity();
        var country = request.getCountry();
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

