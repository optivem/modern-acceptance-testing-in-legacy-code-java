package com.optivem.eshop.systemtest.core.shop.driver;

import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.requests.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.PlaceOrderResponse;
import com.optivem.lang.Error;
import com.optivem.lang.Result;

public interface ShopDriver extends AutoCloseable {
    Result<Void, Error> goToShop();

    Result<PlaceOrderResponse, Error> placeOrder(PlaceOrderRequest request);

    Result<Void, Error> cancelOrder(String orderNumber);

    Result<GetOrderResponse, Error> viewOrder(String orderNumber);
}
