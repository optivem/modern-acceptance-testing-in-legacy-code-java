package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.commons.results.Result;

public interface ShopDriver extends AutoCloseable {
    Result<Void> goToShop();

    Result<PlaceOrderResponse> placeOrder(String sku, String quantity, String country);

    Result<Void> cancelOrder(String orderNumber);

    Result<GetOrderResponse> viewOrder(String orderNumber);
}
