package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.dsl.orders.verifications.ViewOrderVerification;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.DEFAULT_COUPON_CODE;

public class ThenOrderBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenBuilder<TSuccessResponse, TSuccessVerification> {
    private final ViewOrderVerification orderVerification;

    public ThenOrderBuilder(ThenClause<TSuccessResponse, TSuccessVerification> thenClause, SystemDsl app, String orderNumber) {
        super(thenClause);
        this.orderVerification = app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed();
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasSku(String expectedSku) {
        orderVerification.sku(expectedSku);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasQuantity(int expectedQuantity) {
        orderVerification.quantity(expectedQuantity);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasCountry(String expectedCountry) {
        orderVerification.country(expectedCountry);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasUnitPrice(double expectedUnitPrice) {
        orderVerification.unitPrice(expectedUnitPrice);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasBasePrice(double expectedBasePrice) {
        orderVerification.basePrice(expectedBasePrice);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasBasePrice(String basePrice) {
        orderVerification.basePrice(basePrice);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasSubtotalPrice(double expectedSubtotalPrice) {
        orderVerification.subtotalPrice(expectedSubtotalPrice);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasSubtotalPrice(String expectedSubtotalPrice) {
        return hasSubtotalPrice(Double.parseDouble(expectedSubtotalPrice));
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasTotalPrice(double expectedTotalPrice) {
        orderVerification.totalPriceGreaterThanZero();
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasStatus(OrderStatus expectedStatus) {
        orderVerification.status(expectedStatus);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasDiscountRateGreaterThanOrEqualToZero() {
        orderVerification.discountRateGreaterThanOrEqualToZero();
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasDiscountRate(double expectedDiscountRate) {
        orderVerification.discountRate(expectedDiscountRate);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasDiscountAmount(double expectedDiscountAmount) {
        orderVerification.discountAmount(expectedDiscountAmount);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasDiscountAmount(String expectedDiscountAmount) {
        orderVerification.discountAmount(expectedDiscountAmount);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasAppliedCoupon(String expectedCouponCode) {
        orderVerification.appliedCouponCode(expectedCouponCode);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasAppliedCoupon() {
        return hasAppliedCoupon(DEFAULT_COUPON_CODE);
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasDiscountAmountGreaterThanOrEqualToZero() {
        orderVerification.discountAmountGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasSubtotalPriceGreaterThanZero() {
        orderVerification.subtotalPriceGreaterThanZero();
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasTaxRate(double expectedTaxRate) {
        orderVerification.taxRate(expectedTaxRate);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasTaxRate(String expectedTaxRate) {
        orderVerification.taxRate(expectedTaxRate);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasTaxRateGreaterThanOrEqualToZero() {
        orderVerification.taxRateGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasTaxAmount(String expectedTaxAmount) {
        orderVerification.taxAmount(expectedTaxAmount);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasTaxAmountGreaterThanOrEqualToZero() {
        orderVerification.taxAmountGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasTotalPrice(String expectedTotalPrice) {
        orderVerification.totalPrice(expectedTotalPrice);
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasTotalPriceGreaterThanZero() {
        orderVerification.totalPriceGreaterThanZero();
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> hasOrderNumberPrefix(String expectedPrefix) {
        orderVerification.orderNumberHasPrefix(expectedPrefix);
        return this;
    }
}
