package com.optivem.eshop.systemtest.core.shop.driver;

import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.requests.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.PlaceOrderResponse;
import com.optivem.lang.Result;

public interface ShopDriver extends AutoCloseable {
    Result<Void> goToShop();

    Result<PlaceOrderResponse> placeOrder(PlaceOrderRequest request);

    Result<Void> cancelOrder(String orderNumber);

    Result<GetOrderResponse> viewOrder(String orderNumber);
}
