package com.optivem.eshop.systemtest.core.shop.driver;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.ViewOrderDetailsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.commons.util.Result;

public interface OrderDriver {
    Result<PlaceOrderResponse, SystemError> placeOrder(PlaceOrderRequest request);

    Result<Void, SystemError> cancelOrder(String orderNumber);

    Result<ViewOrderDetailsResponse, SystemError> viewOrder(String orderNumber);
}
