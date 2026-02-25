package com.optivem.eshop.systemtest.dsl.api.given.steps;

import com.optivem.eshop.systemtest.dsl.api.given.steps.base.GivenStep;
import com.optivem.eshop.systemtest.driver.api.shop.dtos.orders.OrderStatus;

public interface GivenOrder extends GivenStep {
    GivenOrder withOrderNumber(String orderNumber);

    GivenOrder withSku(String sku);

    GivenOrder withQuantity(String quantity);

    GivenOrder withQuantity(int quantity);

    GivenOrder withCountry(String country);

    GivenOrder withCouponCode(String couponCode);

    GivenOrder withStatus(OrderStatus status);

}

