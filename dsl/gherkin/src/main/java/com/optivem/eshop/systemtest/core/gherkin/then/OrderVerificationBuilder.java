package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;

public class OrderVerificationBuilder {
    private final SystemDsl app;
    private final String orderNumber;

    public OrderVerificationBuilder(SystemDsl app, String orderNumber) {
        this.app = app;
        this.orderNumber = orderNumber;
    }

    public OrderVerificationBuilder hasSku(String expectedSku) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .sku(expectedSku);
        return this;
    }

    public OrderVerificationBuilder hasQuantity(int expectedQuantity) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .quantity(expectedQuantity);
        return this;
    }

    public OrderVerificationBuilder hasCountry(String expectedCountry) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .country(expectedCountry);
        return this;
    }

    public OrderVerificationBuilder hasUnitPrice(double expectedUnitPrice) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .unitPrice(expectedUnitPrice);
        return this;
    }

    public OrderVerificationBuilder shouldHaveOriginalPrice(double expectedOriginalPrice) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .originalPrice(expectedOriginalPrice);
        return this;
    }

    public OrderVerificationBuilder shouldHaveOriginalPrice(String expectedOriginalPrice) {
        return shouldHaveOriginalPrice(Double.parseDouble(expectedOriginalPrice));
    }

    public OrderVerificationBuilder hasTotalPrice(double expectedTotalPrice) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .totalPriceGreaterThanZero();
        return this;
    }

    public OrderVerificationBuilder hasStatus(OrderStatus expectedStatus) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .status(expectedStatus);
        return this;
    }

    public OrderVerificationBuilder hasDiscountRateGreaterThanOrEqualToZero() {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .discountRateGreaterThanOrEqualToZero();
        return this;
    }

    public OrderVerificationBuilder hasDiscountRate(double expectedDiscountRate) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .discountRate(expectedDiscountRate);
        return this;
    }

    public OrderVerificationBuilder hasDiscountAmountGreaterThanOrEqualToZero() {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .discountAmountGreaterThanOrEqualToZero();
        return this;
    }

    public OrderVerificationBuilder hasSubtotalPriceGreaterThanZero() {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .subtotalPriceGreaterThanZero();
        return this;
    }

    public OrderVerificationBuilder hasTaxRateGreaterThanOrEqualToZero() {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .taxRateGreaterThanOrEqualToZero();
        return this;
    }

    public OrderVerificationBuilder hasTaxAmountGreaterThanOrEqualToZero() {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .taxAmountGreaterThanOrEqualToZero();
        return this;
    }

    public OrderVerificationBuilder hasTotalPriceGreaterThanZero() {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .totalPriceGreaterThanZero();
        return this;
    }

    public OrderVerificationBuilder expectOrderNumberPrefix(String expectedPrefix) {
        if (!orderNumber.startsWith(expectedPrefix)) {
            throw new AssertionError("Expected order number to start with '" + expectedPrefix + "' but was: " + orderNumber);
        }
        return this;
    }
}
