package com.optivem.eshop.systemtest.dsl.api;

public interface WhenPlaceOrderPort {
    WhenPlaceOrderPort withOrderNumber(String orderNumber);

    WhenPlaceOrderPort withSku(String sku);

    WhenPlaceOrderPort withQuantity(String quantity);

    WhenPlaceOrderPort withQuantity(int quantity);

    WhenPlaceOrderPort withCountry(String country);

    WhenPlaceOrderPort withCouponCode(String couponCode);

    WhenPlaceOrderPort withCouponCode();

    ThenPort then();
}
