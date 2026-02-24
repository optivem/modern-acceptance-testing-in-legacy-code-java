package com.optivem.eshop.systemtest.driver.api.shop.driver;

import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.coupons.PublishCouponRequest;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.errors.SystemError;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.orders.PlaceOrderResponse;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.orders.ViewOrderResponse;
import com.optivem.commons.util.Result;

public interface ShopDriver extends AutoCloseable {
    Result<Void, SystemError> goToShop();

    Result<PlaceOrderResponse, SystemError> placeOrder(PlaceOrderRequest request);

    Result<Void, SystemError> cancelOrder(String orderNumber);

    Result<ViewOrderResponse, SystemError> viewOrder(String orderNumber);

    Result<Void, SystemError> publishCoupon(PublishCouponRequest request);

    Result<BrowseCouponsResponse, SystemError> browseCoupons();
}
