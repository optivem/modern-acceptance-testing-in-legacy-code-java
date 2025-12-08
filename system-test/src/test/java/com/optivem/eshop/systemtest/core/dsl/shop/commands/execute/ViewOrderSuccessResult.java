package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.drivers.system.commons.enums.OrderStatus;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.BaseSuccessResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewOrderSuccessResult extends BaseSuccessResult<GetOrderResponse> {

    public ViewOrderSuccessResult(GetOrderResponse response, DslContext context) {
        super(response, context);
    }

    public ViewOrderSuccessResult expectOrderNumber(String orderNumberResultAlias) {
        var expectedOrderNumber = context.results().getAliasValue(orderNumberResultAlias);
        assertThat(response.getOrderNumber()).isEqualTo(expectedOrderNumber);
        return this;
    }

    public ViewOrderSuccessResult expectSku(String skuParamAlias) {
        var expectedSku = context.params().getOrGenerateAliasValue(skuParamAlias);
        assertThat(response.getSku()).isEqualTo(expectedSku);
        return this;
    }

    public ViewOrderSuccessResult expectQuantity(int expectedQuantity) {
        assertThat(response.getQuantity()).isEqualTo(expectedQuantity);
        return this;
    }

    public ViewOrderSuccessResult expectCountry(String expectedCountry) {
        assertThat(response.getCountry()).isEqualTo(expectedCountry);
        return this;
    }

    public ViewOrderSuccessResult expectUnitPrice(String expectedUnitPrice) {
        assertThat(response.getUnitPrice()).isEqualTo(new BigDecimal(expectedUnitPrice));
        return this;
    }

    public ViewOrderSuccessResult expectOriginalPrice(String expectedOriginalPrice) {
        assertThat(response.getOriginalPrice()).isEqualTo(new BigDecimal(expectedOriginalPrice));
        return this;
    }

    public ViewOrderSuccessResult expectStatus(OrderStatus expectedStatus) {
        assertThat(response.getStatus()).isEqualTo(expectedStatus);
        return this;
    }

    public ViewOrderSuccessResult expectDiscountRateGreaterThanOrEqualToZero() {
        var discountRate = response.getDiscountRate();
        assertThat(discountRate)
                .withFailMessage("Discount rate should be non-negative, but was: %s", discountRate)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderSuccessResult expectDiscountAmountGreaterThanOrEqualToZero() {
        var discountAmount = response.getDiscountAmount();
        assertThat(discountAmount)
                .withFailMessage("Discount amount should be non-negative, but was: %s", discountAmount)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderSuccessResult expectSubtotalPriceGreaterThanZero() {
        var subtotalPrice = response.getSubtotalPrice();
        assertThat(subtotalPrice)
                .withFailMessage("Subtotal price should be positive, but was: %s", subtotalPrice)
                .isGreaterThan(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderSuccessResult expectTaxRateGreaterThanOrEqualToZero() {
        var taxRate = response.getTaxRate();
        assertThat(taxRate)
                .withFailMessage("Tax rate should be non-negative, but was: %s", taxRate)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderSuccessResult expectTaxAmountGreaterThanOrEqualToZero() {
        var taxAmount = response.getTaxAmount();
        assertThat(taxAmount)
                .withFailMessage("Tax amount should be non-negative, but was: %s", taxAmount)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderSuccessResult expectTotalPriceGreaterThanZero() {
        var totalPrice = response.getTotalPrice();
        assertThat(totalPrice)
                .withFailMessage("Total price should be positive, but was: %s", totalPrice)
                .isGreaterThan(BigDecimal.ZERO);
        return this;
    }
}

