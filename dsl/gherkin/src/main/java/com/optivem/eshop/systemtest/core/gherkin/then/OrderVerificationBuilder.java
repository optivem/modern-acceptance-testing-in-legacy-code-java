package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.dsl.verifications.ViewOrderVerification;

public class OrderVerificationBuilder {
    private final ViewOrderVerification orderVerification;

    public OrderVerificationBuilder(SystemDsl app, String orderNumber) {
        this.orderVerification = app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed();
    }

    public OrderVerificationBuilder hasSku(String expectedSku) {
        orderVerification.sku(expectedSku);
        return this;
    }

    public OrderVerificationBuilder hasQuantity(int expectedQuantity) {
        orderVerification.quantity(expectedQuantity);
        return this;
    }

    public OrderVerificationBuilder hasCountry(String expectedCountry) {
        orderVerification.country(expectedCountry);
        return this;
    }

    public OrderVerificationBuilder hasUnitPrice(double expectedUnitPrice) {
        orderVerification.unitPrice(expectedUnitPrice);
        return this;
    }

    public OrderVerificationBuilder shouldHaveOriginalPrice(double expectedOriginalPrice) {
        orderVerification.originalPrice(expectedOriginalPrice);
        return this;
    }

    public OrderVerificationBuilder shouldHaveOriginalPrice(String expectedOriginalPrice) {
        return shouldHaveOriginalPrice(Double.parseDouble(expectedOriginalPrice));
    }

    public OrderVerificationBuilder hasTotalPrice(double expectedTotalPrice) {
        orderVerification.totalPriceGreaterThanZero();
        return this;
    }

    public OrderVerificationBuilder hasStatus(OrderStatus expectedStatus) {
        orderVerification.status(expectedStatus);
        return this;
    }

    public OrderVerificationBuilder hasDiscountRateGreaterThanOrEqualToZero() {
        orderVerification.discountRateGreaterThanOrEqualToZero();
        return this;
    }

    public OrderVerificationBuilder hasDiscountRate(double expectedDiscountRate) {
        orderVerification.discountRate(expectedDiscountRate);
        return this;
    }

    public OrderVerificationBuilder hasDiscountAmountGreaterThanOrEqualToZero() {
        orderVerification.discountAmountGreaterThanOrEqualToZero();
        return this;
    }

    public OrderVerificationBuilder hasSubtotalPriceGreaterThanZero() {
        orderVerification.subtotalPriceGreaterThanZero();
        return this;
    }

    public OrderVerificationBuilder hasTaxRateGreaterThanOrEqualToZero() {
        orderVerification.taxRateGreaterThanOrEqualToZero();
        return this;
    }

    public OrderVerificationBuilder hasTaxAmountGreaterThanOrEqualToZero() {
        orderVerification.taxAmountGreaterThanOrEqualToZero();
        return this;
    }

    public OrderVerificationBuilder hasTotalPriceGreaterThanZero() {
        orderVerification.totalPriceGreaterThanZero();
        return this;
    }

    public OrderVerificationBuilder expectOrderNumberPrefix(String expectedPrefix) {
        orderVerification.orderNumberHasPrefix(expectedPrefix);
        return this;
    }

    public OrderVerificationBuilder and() {
        return this;
    }
}
