package com.optivem.eshop.systemtest.dsl.api.when.steps;

import com.optivem.eshop.systemtest.dsl.api.then.Then;

public interface WhenPlaceOrder {
    WhenPlaceOrder withOrderNumber(String orderNumber);

    WhenPlaceOrder withSku(String sku);

    WhenPlaceOrder withQuantity(String quantity);

    WhenPlaceOrder withQuantity(int quantity);

    WhenPlaceOrder withCountry(String country);

    WhenPlaceOrder withCouponCode(String couponCode);

    WhenPlaceOrder withCouponCode();

    Then then();
}

