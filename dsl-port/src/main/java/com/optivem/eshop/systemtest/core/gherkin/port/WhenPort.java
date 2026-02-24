package com.optivem.eshop.systemtest.core.gherkin.port;

public interface WhenPort {
    WhenActionPort goToShop();

    WhenPlaceOrderPort placeOrder();

    WhenCancelOrderPort cancelOrder();

    WhenViewOrderPort viewOrder();

    WhenPublishCouponPort publishCoupon();

    WhenActionPort browseCoupons();
}
