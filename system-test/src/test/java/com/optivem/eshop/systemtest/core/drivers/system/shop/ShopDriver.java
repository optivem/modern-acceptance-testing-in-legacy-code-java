package com.optivem.eshop.systemtest.core.drivers.system.shop;

import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.results.Result;

public interface ShopDriver extends AutoCloseable {
    Result<Void> goToShop();

    Result<PlaceOrderResponse> placeOrder(PlaceOrderRequest request);

    Result<Void> cancelOrder(String orderNumber);

    Result<GetOrderResponse> viewOrder(String orderNumber);
}
