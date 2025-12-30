package com.optivem.eshop.systemtest.core.shop.driver;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.ViewOrderDetailsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.error.SystemError;
import com.optivem.lang.Result;

public interface OrderDriver {
    Result<PlaceOrderResponse, SystemError> placeOrder(PlaceOrderRequest request);

    Result<Void, SystemError> cancelOrder(String orderNumber);

    Result<ViewOrderDetailsResponse, SystemError> viewOrder(String orderNumber);
}
