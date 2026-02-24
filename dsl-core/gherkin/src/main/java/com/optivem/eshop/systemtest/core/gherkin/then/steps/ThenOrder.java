package com.optivem.eshop.systemtest.core.gherkin.then.steps;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.dsl.api.then.steps.ThenOrderPort;
import com.optivem.eshop.systemtest.driver.api.shop.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.PlaceOrderVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.ViewOrderVerification;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.DEFAULT_COUPON_CODE;

public class ThenOrder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenStep<TSuccessResponse, TSuccessVerification> implements ThenOrderPort {
    private final ViewOrderVerification orderVerification;

    public ThenOrder(SystemDsl app, ExecutionResultContext executionResult, String orderNumber, TSuccessVerification successVerification) {
        super(app, executionResult, successVerification);
        if (orderNumber == null) {
            throw new IllegalStateException("Cannot verify order: no order number available from the executed operation");
        }
        this.orderVerification = app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed();
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasSku(String expectedSku) {
        orderVerification.sku(expectedSku);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasQuantity(int expectedQuantity) {
        orderVerification.quantity(expectedQuantity);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasCountry(String expectedCountry) {
        orderVerification.country(expectedCountry);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasUnitPrice(double expectedUnitPrice) {
        orderVerification.unitPrice(expectedUnitPrice);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasBasePrice(double expectedBasePrice) {
        orderVerification.basePrice(expectedBasePrice);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasBasePrice(String basePrice) {
        orderVerification.basePrice(basePrice);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasSubtotalPrice(double expectedSubtotalPrice) {
        orderVerification.subtotalPrice(expectedSubtotalPrice);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasSubtotalPrice(String expectedSubtotalPrice) {
        return hasSubtotalPrice(Double.parseDouble(expectedSubtotalPrice));
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasTotalPrice(double expectedTotalPrice) {
        orderVerification.totalPrice(expectedTotalPrice);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasStatus(OrderStatus expectedStatus) {
        orderVerification.status(expectedStatus);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasDiscountRateGreaterThanOrEqualToZero() {
        orderVerification.discountRateGreaterThanOrEqualToZero();
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasDiscountRate(double expectedDiscountRate) {
        orderVerification.discountRate(expectedDiscountRate);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasDiscountAmount(double expectedDiscountAmount) {
        orderVerification.discountAmount(expectedDiscountAmount);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasDiscountAmount(String expectedDiscountAmount) {
        orderVerification.discountAmount(expectedDiscountAmount);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasAppliedCoupon(String expectedCouponCode) {
        orderVerification.appliedCouponCode(expectedCouponCode);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasAppliedCoupon() {
        return hasAppliedCoupon(DEFAULT_COUPON_CODE);
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasDiscountAmountGreaterThanOrEqualToZero() {
        orderVerification.discountAmountGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrder<TSuccessResponse, TSuccessVerification> hasSubtotalPriceGreaterThanZero() {
        orderVerification.subtotalPriceGreaterThanZero();
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasTaxRate(double expectedTaxRate) {
        orderVerification.taxRate(expectedTaxRate);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasTaxRate(String expectedTaxRate) {
        orderVerification.taxRate(expectedTaxRate);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasTaxRateGreaterThanOrEqualToZero() {
        orderVerification.taxRateGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrder<TSuccessResponse, TSuccessVerification> hasTaxAmount(String expectedTaxAmount) {
        orderVerification.taxAmount(expectedTaxAmount);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasTaxAmountGreaterThanOrEqualToZero() {
        orderVerification.taxAmountGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrder<TSuccessResponse, TSuccessVerification> hasTotalPrice(String expectedTotalPrice) {
        orderVerification.totalPrice(expectedTotalPrice);
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasTotalPriceGreaterThanZero() {
        orderVerification.totalPriceGreaterThanZero();
        return this;
    }

    public ThenOrder<TSuccessResponse, TSuccessVerification> hasOrderNumberPrefix(String expectedPrefix) {
        switch (successVerification) {
            case PlaceOrderVerification placeOrderVerification -> placeOrderVerification.orderNumberStartsWith(expectedPrefix);
            case ViewOrderVerification viewOrderVerification -> viewOrderVerification.orderNumberHasPrefix(expectedPrefix);
            case null -> { /* no-op: orderNumber prefix not applicable */ }
            default -> { /* no-op: verification type has no order number prefix check */ }
        }
        orderVerification.orderNumberHasPrefix(expectedPrefix);
        return this;
    }

    @Override
    public ThenOrder<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}
