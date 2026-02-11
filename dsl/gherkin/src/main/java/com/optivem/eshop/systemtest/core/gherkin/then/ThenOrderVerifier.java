package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.dsl.orders.verifications.PlaceOrderVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.orders.verifications.ViewOrderVerification;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.DEFAULT_COUPON_CODE;

public class ThenOrderVerifier<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenVerifier<TSuccessResponse, TSuccessVerification> {
    private final ViewOrderVerification orderVerification;

    public ThenOrderVerifier(SystemDsl app, ExecutionResultContext executionResult, String orderNumber, TSuccessVerification successVerification) {
        super(app, executionResult, successVerification);
        if (orderNumber == null) {
            throw new IllegalStateException("Cannot verify order: no order number available from the executed operation");
        }
        this.orderVerification = app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed();
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasSku(String expectedSku) {
        orderVerification.sku(expectedSku);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasQuantity(int expectedQuantity) {
        orderVerification.quantity(expectedQuantity);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasCountry(String expectedCountry) {
        orderVerification.country(expectedCountry);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasUnitPrice(double expectedUnitPrice) {
        orderVerification.unitPrice(expectedUnitPrice);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasBasePrice(double expectedBasePrice) {
        orderVerification.basePrice(expectedBasePrice);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasBasePrice(String basePrice) {
        orderVerification.basePrice(basePrice);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasSubtotalPrice(double expectedSubtotalPrice) {
        orderVerification.subtotalPrice(expectedSubtotalPrice);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasSubtotalPrice(String expectedSubtotalPrice) {
        return hasSubtotalPrice(Double.parseDouble(expectedSubtotalPrice));
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasTotalPrice(double expectedTotalPrice) {
        orderVerification.totalPrice(expectedTotalPrice);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasStatus(OrderStatus expectedStatus) {
        orderVerification.status(expectedStatus);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasDiscountRateGreaterThanOrEqualToZero() {
        orderVerification.discountRateGreaterThanOrEqualToZero();
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasDiscountRate(double expectedDiscountRate) {
        orderVerification.discountRate(expectedDiscountRate);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasDiscountAmount(double expectedDiscountAmount) {
        orderVerification.discountAmount(expectedDiscountAmount);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasDiscountAmount(String expectedDiscountAmount) {
        orderVerification.discountAmount(expectedDiscountAmount);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasAppliedCoupon(String expectedCouponCode) {
        orderVerification.appliedCouponCode(expectedCouponCode);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasAppliedCoupon() {
        return hasAppliedCoupon(DEFAULT_COUPON_CODE);
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasDiscountAmountGreaterThanOrEqualToZero() {
        orderVerification.discountAmountGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasSubtotalPriceGreaterThanZero() {
        orderVerification.subtotalPriceGreaterThanZero();
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasTaxRate(double expectedTaxRate) {
        orderVerification.taxRate(expectedTaxRate);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasTaxRate(String expectedTaxRate) {
        orderVerification.taxRate(expectedTaxRate);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasTaxRateGreaterThanOrEqualToZero() {
        orderVerification.taxRateGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasTaxAmount(String expectedTaxAmount) {
        orderVerification.taxAmount(expectedTaxAmount);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasTaxAmountGreaterThanOrEqualToZero() {
        orderVerification.taxAmountGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasTotalPrice(String expectedTotalPrice) {
        orderVerification.totalPrice(expectedTotalPrice);
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasTotalPriceGreaterThanZero() {
        orderVerification.totalPriceGreaterThanZero();
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> hasOrderNumberPrefix(String expectedPrefix) {
        switch (successVerification) {
            case PlaceOrderVerification placeOrderVerification -> placeOrderVerification.orderNumberStartsWith(expectedPrefix);
            case ViewOrderVerification viewOrderVerification -> viewOrderVerification.orderNumberHasPrefix(expectedPrefix);
            case null -> { /* no-op: orderNumber prefix not applicable */ }
            default -> { /* no-op: verification type has no order number prefix check */ }
        }
        orderVerification.orderNumberHasPrefix(expectedPrefix);
        return this;
    }
}
