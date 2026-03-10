package com.optivem.eshop.systemtest.driver.port.shop;

import com.optivem.eshop.systemtest.driver.port.shared.dtos.ErrorResponse;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.PublishCouponRequest;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.SubmitReviewRequest;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.SubmitReviewResponse;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.ViewOrderResponse;
import com.optivem.common.Result;

public interface ShopDriver extends AutoCloseable {
    Result<Void, ErrorResponse> goToShop();

    Result<PlaceOrderResponse, ErrorResponse> placeOrder(PlaceOrderRequest request);

    Result<Void, ErrorResponse> cancelOrder(String orderNumber);

    Result<Void, ErrorResponse> deliverOrder(String orderNumber);

    Result<ViewOrderResponse, ErrorResponse> viewOrder(String orderNumber);

    Result<Void, ErrorResponse> publishCoupon(PublishCouponRequest request);

    Result<BrowseCouponsResponse, ErrorResponse> browseCoupons();

    Result<SubmitReviewResponse, ErrorResponse> submitReview(SubmitReviewRequest request);
}
