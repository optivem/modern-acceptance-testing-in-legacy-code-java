package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.dsl.orders.verifications.ViewOrderVerification;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.DEFAULT_COUPON_CODE;

public class ThenOrderVerifier extends BaseThenStateVerifier {
    private final ViewOrderVerification orderVerification;

    public ThenOrderVerifier(SystemDsl app, ExecutionResultContext executionResult, String orderNumber) {
        super(app, executionResult);
        this.orderVerification = app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed();
    }

    public ThenOrderVerifier hasSku(String expectedSku) {
        orderVerification.sku(expectedSku);
        return this;
    }

    public ThenOrderVerifier hasQuantity(int expectedQuantity) {
        orderVerification.quantity(expectedQuantity);
        return this;
    }

    public ThenOrderVerifier hasCountry(String expectedCountry) {
        orderVerification.country(expectedCountry);
        return this;
    }

    public ThenOrderVerifier hasUnitPrice(double expectedUnitPrice) {
        orderVerification.unitPrice(expectedUnitPrice);
        return this;
    }

    public ThenOrderVerifier hasBasePrice(double expectedBasePrice) {
        orderVerification.basePrice(expectedBasePrice);
        return this;
    }

    public ThenOrderVerifier hasBasePrice(String basePrice) {
        orderVerification.basePrice(basePrice);
        return this;
    }

    public ThenOrderVerifier hasSubtotalPrice(double expectedSubtotalPrice) {
        orderVerification.subtotalPrice(expectedSubtotalPrice);
        return this;
    }

    public ThenOrderVerifier hasSubtotalPrice(String expectedSubtotalPrice) {
        return hasSubtotalPrice(Double.parseDouble(expectedSubtotalPrice));
    }

    public ThenOrderVerifier hasTotalPrice(double expectedTotalPrice) {
        orderVerification.totalPrice(expectedTotalPrice);
        return this;
    }

    public ThenOrderVerifier hasStatus(OrderStatus expectedStatus) {
        orderVerification.status(expectedStatus);
        return this;
    }

    public ThenOrderVerifier hasDiscountRateGreaterThanOrEqualToZero() {
        orderVerification.discountRateGreaterThanOrEqualToZero();
        return this;
    }

    public ThenOrderVerifier hasDiscountRate(double expectedDiscountRate) {
        orderVerification.discountRate(expectedDiscountRate);
        return this;
    }

    public ThenOrderVerifier hasDiscountAmount(double expectedDiscountAmount) {
        orderVerification.discountAmount(expectedDiscountAmount);
        return this;
    }

    public ThenOrderVerifier hasDiscountAmount(String expectedDiscountAmount) {
        orderVerification.discountAmount(expectedDiscountAmount);
        return this;
    }

    public ThenOrderVerifier hasAppliedCoupon(String expectedCouponCode) {
        orderVerification.appliedCouponCode(expectedCouponCode);
        return this;
    }

    public ThenOrderVerifier hasAppliedCoupon() {
        return hasAppliedCoupon(DEFAULT_COUPON_CODE);
    }

    public ThenOrderVerifier hasDiscountAmountGreaterThanOrEqualToZero() {
        orderVerification.discountAmountGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrderVerifier hasSubtotalPriceGreaterThanZero() {
        orderVerification.subtotalPriceGreaterThanZero();
        return this;
    }

    public ThenOrderVerifier hasTaxRate(double expectedTaxRate) {
        orderVerification.taxRate(expectedTaxRate);
        return this;
    }

    public ThenOrderVerifier hasTaxRate(String expectedTaxRate) {
        orderVerification.taxRate(expectedTaxRate);
        return this;
    }

    public ThenOrderVerifier hasTaxRateGreaterThanOrEqualToZero() {
        orderVerification.taxRateGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrderVerifier hasTaxAmount(String expectedTaxAmount) {
        orderVerification.taxAmount(expectedTaxAmount);
        return this;
    }

    public ThenOrderVerifier hasTaxAmountGreaterThanOrEqualToZero() {
        orderVerification.taxAmountGreaterThanOrEqualToZero();
        return this;
    }


    public ThenOrderVerifier hasTotalPrice(String expectedTotalPrice) {
        orderVerification.totalPrice(expectedTotalPrice);
        return this;
    }

    public ThenOrderVerifier hasTotalPriceGreaterThanZero() {
        orderVerification.totalPriceGreaterThanZero();
        return this;
    }

    public ThenOrderVerifier hasOrderNumberPrefix(String expectedPrefix) {
        orderVerification.orderNumberHasPrefix(expectedPrefix);
        return this;
    }
}
