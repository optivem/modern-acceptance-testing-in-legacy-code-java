package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.dsl.verifications.ViewOrderVerification;

public class ThenOrderBuilder extends BaseThenBuilder {
    private final ViewOrderVerification orderVerification;

    public ThenOrderBuilder(ThenClause thenClause, SystemDsl app, String orderNumber) {
        super(thenClause);
        this.orderVerification = app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed();
    }

    public ThenOrderBuilder hasSku(String expectedSku) {
        orderVerification.sku(expectedSku);
        return this;
    }

    public ThenOrderBuilder hasQuantity(int expectedQuantity) {
        orderVerification.quantity(expectedQuantity);
        return this;
    }

    public ThenOrderBuilder hasCountry(String expectedCountry) {
        orderVerification.country(expectedCountry);
        return this;
    }

    public ThenOrderBuilder hasUnitPrice(double expectedUnitPrice) {
        orderVerification.unitPrice(expectedUnitPrice);
        return this;
    }

    public ThenOrderBuilder shouldHaveBasePrice(double expectedBasePrice) {
        orderVerification.basePrice(expectedBasePrice);
        return this;
    }

    public ThenOrderBuilder shouldHaveBasePrice(String basePrice) {
        orderVerification.basePrice(basePrice);
        return this;
    }

    public ThenOrderBuilder shouldHaveSubtotalPrice(double expectedSubtotalPrice) {
        orderVerification.subtotalPrice(expectedSubtotalPrice);
        return this;
    }

    public ThenOrderBuilder shouldHaveSubtotalPrice(String expectedSubtotalPrice) {
        return shouldHaveSubtotalPrice(Double.parseDouble(expectedSubtotalPrice));
    }

    public ThenOrderBuilder hasTotalPrice(double expectedTotalPrice) {
        orderVerification.totalPriceGreaterThanZero();
        return this;
    }

    public ThenOrderBuilder hasStatus(OrderStatus expectedStatus) {
        orderVerification.status(expectedStatus);
        return this;
    }

    public ThenOrderBuilder hasDiscountRateGreaterThanOrEqualToZero() {
        orderVerification.discountRateGreaterThanOrEqualToZero();
        return this;
    }

    public ThenOrderBuilder hasDiscountRate(double expectedDiscountRate) {
        orderVerification.discountRate(expectedDiscountRate);
        return this;
    }

    public ThenOrderBuilder hasDiscountAmount(double expectedDiscountAmount) {
        orderVerification.discountAmount(expectedDiscountAmount);
        return this;
    }

    public ThenOrderBuilder hasDiscountAmount(String expectedDiscountAmount) {
        orderVerification.discountAmount(expectedDiscountAmount);
        return this;
    }

    public ThenOrderBuilder hasAppliedCoupon(String expectedCouponCode) {
        orderVerification.appliedCouponCode(expectedCouponCode);
        return this;
    }

    public ThenOrderBuilder hasDiscountAmountGreaterThanOrEqualToZero() {
        orderVerification.discountAmountGreaterThanOrEqualToZero();
        return this;
    }

    public ThenOrderBuilder hasSubtotalPrice(String expectedSubtotalPrice) {
        orderVerification.subtotalPrice(expectedSubtotalPrice);
        return this;
    }

    public ThenOrderBuilder hasSubtotalPriceGreaterThanZero() {
        orderVerification.subtotalPriceGreaterThanZero();
        return this;
    }

    public ThenOrderBuilder hasTaxRate(double expectedTaxRate) {
        orderVerification.taxRate(expectedTaxRate);
        return this;
    }

    public ThenOrderBuilder hasTaxRate(String expectedTaxRate) {
        orderVerification.taxRate(expectedTaxRate);
        return this;
    }

    public ThenOrderBuilder hasTaxRateGreaterThanOrEqualToZero() {
        orderVerification.taxRateGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrderBuilder hasTaxAmount(String expectedTaxAmount) {
        orderVerification.taxAmount(expectedTaxAmount);
        return this;
    }

    public ThenOrderBuilder hasTaxAmountGreaterThanOrEqualToZero() {
        orderVerification.taxAmountGreaterThanOrEqualToZero();
        return this;
    }

    public ThenOrderBuilder hasTotalPriceGreaterThanZero() {
        orderVerification.totalPriceGreaterThanZero();
        return this;
    }

    public ThenOrderBuilder expectOrderNumberPrefix(String expectedPrefix) {
        orderVerification.orderNumberHasPrefix(expectedPrefix);
        return this;
    }


}
