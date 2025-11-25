package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpUtils;
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
        var httpResponse = apiClient.echo().echo();
        return TestHttpUtils.getOkResultOrFailure(httpResponse);
    }

    @Override
    public Result<PlaceOrderResponse> placeOrder(String sku, String quantity, String country) {
        var httpResponse = apiClient.orders().placeOrder(sku, quantity, country);
        return TestHttpUtils.getCreatedResultOrFailure(httpResponse, PlaceOrderResponse.class);
    }


    @Override
    public Result<Void> cancelOrder(String orderNumber) {
        var httpResponse = apiClient.orders().cancelOrder(orderNumber);
        return TestHttpUtils.getNoContentResultOrFailure(httpResponse);
    }

    @Override
    public Result<GetOrderResponse> viewOrder(String orderNumber) {
        var httpResponse = apiClient.orders().viewOrder(orderNumber);
        return TestHttpUtils.getOkResultOrFailure(httpResponse, GetOrderResponse.class);
    }

    @Override
    public void close() {
        apiClient.close();
    }
}

