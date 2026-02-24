package com.optivem.eshop.systemtest.dsl.api;

public interface WhenPort {
    WhenActionPort goToShop();

    WhenPlaceOrderPort placeOrder();

    WhenCancelOrderPort cancelOrder();

    WhenViewOrderPort viewOrder();

    WhenPublishCouponPort publishCoupon();

    WhenActionPort browseCoupons();
}
