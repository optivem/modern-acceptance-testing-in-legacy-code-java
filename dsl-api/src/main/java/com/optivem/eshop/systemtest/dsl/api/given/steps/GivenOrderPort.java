package com.optivem.eshop.systemtest.dsl.api.given.steps;

import com.optivem.eshop.systemtest.driver.api.shop.dtos.orders.OrderStatus;

public interface GivenOrderPort extends GivenStepPort {
    GivenOrderPort withOrderNumber(String orderNumber);

    GivenOrderPort withSku(String sku);

    GivenOrderPort withQuantity(String quantity);

    GivenOrderPort withQuantity(int quantity);

    GivenOrderPort withCountry(String country);

    GivenOrderPort withCouponCode(String couponCode);

    GivenOrderPort withStatus(OrderStatus status);

}
