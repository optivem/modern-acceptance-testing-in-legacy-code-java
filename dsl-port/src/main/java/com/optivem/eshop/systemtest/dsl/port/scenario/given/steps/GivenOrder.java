package com.optivem.eshop.systemtest.dsl.port.scenario.given.steps;

import com.optivem.eshop.systemtest.dsl.port.scenario.given.steps.base.GivenStep;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.OrderStatus;

public interface GivenOrder extends GivenStep {
    GivenOrder withOrderNumber(String orderNumber);

    GivenOrder withSku(String sku);

    GivenOrder withQuantity(String quantity);

    GivenOrder withQuantity(int quantity);

    GivenOrder withCountry(String country);

    GivenOrder withCouponCode(String couponCode);

    GivenOrder withStatus(OrderStatus status);
}
