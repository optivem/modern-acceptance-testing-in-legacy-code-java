package com.optivem.eshop.systemtest.dsl.api;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;

public interface ThenOrderPort {
    ThenOrderPort and();

    ThenOrderPort hasSku(String expectedSku);

    ThenOrderPort hasQuantity(int expectedQuantity);

    ThenOrderPort hasCountry(String expectedCountry);

    ThenOrderPort hasUnitPrice(double expectedUnitPrice);

    ThenOrderPort hasBasePrice(double expectedBasePrice);

    ThenOrderPort hasBasePrice(String basePrice);

    ThenOrderPort hasSubtotalPrice(double expectedSubtotalPrice);

    ThenOrderPort hasSubtotalPrice(String expectedSubtotalPrice);

    ThenOrderPort hasTotalPrice(double expectedTotalPrice);

    ThenOrderPort hasTotalPrice(String expectedTotalPrice);

    ThenOrderPort hasStatus(OrderStatus expectedStatus);

    ThenOrderPort hasDiscountRateGreaterThanOrEqualToZero();

    ThenOrderPort hasDiscountRate(double expectedDiscountRate);

    ThenOrderPort hasDiscountAmount(double expectedDiscountAmount);

    ThenOrderPort hasDiscountAmount(String expectedDiscountAmount);

    ThenOrderPort hasAppliedCoupon(String expectedCouponCode);

    ThenOrderPort hasAppliedCoupon();

    ThenOrderPort hasDiscountAmountGreaterThanOrEqualToZero();

    ThenOrderPort hasSubtotalPriceGreaterThanZero();

    ThenOrderPort hasTaxRate(double expectedTaxRate);

    ThenOrderPort hasTaxRate(String expectedTaxRate);

    ThenOrderPort hasTaxRateGreaterThanOrEqualToZero();

    ThenOrderPort hasTaxAmount(String expectedTaxAmount);

    ThenOrderPort hasTaxAmountGreaterThanOrEqualToZero();

    ThenOrderPort hasTotalPriceGreaterThanZero();

    ThenOrderPort hasOrderNumberPrefix(String expectedPrefix);
}
