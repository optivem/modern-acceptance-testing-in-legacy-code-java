package com.optivem.eshop.systemtest.core.shop.dsl.orders;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.ViewOrderResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.util.Converter;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewOrderVerification extends ResponseVerification<ViewOrderResponse> {

    public ViewOrderVerification(ViewOrderResponse response, UseCaseContext context) {
        super(response, context);
    }

    public ViewOrderVerification orderNumber(String orderNumberResultAlias) {
        var expectedOrderNumber = context.getResultValue(orderNumberResultAlias);
        var actualOrderNumber = response.getOrderNumber();
        assertThat(actualOrderNumber)
                .withFailMessage("Expected order number to be '%s', but was '%s'", expectedOrderNumber, actualOrderNumber)
                .isEqualTo(expectedOrderNumber);
        return this;
    }

    public ViewOrderVerification sku(String skuParamAlias) {
        var expectedSku = context.getParamValue(skuParamAlias);
        var actualSku = response.getSku();
        assertThat(actualSku)
                .withFailMessage("Expected SKU to be '%s', but was '%s'", expectedSku, actualSku)
                .isEqualTo(expectedSku);
        return this;
    }

    public ViewOrderVerification quantity(int expectedQuantity) {
        var actualQuantity = response.getQuantity();
        assertThat(actualQuantity)
                .withFailMessage("Expected quantity to be %d, but was %d", expectedQuantity, actualQuantity)
                .isEqualTo(expectedQuantity);
        return this;
    }

    public ViewOrderVerification quantity(String expectedQuantity) {
        return quantity(Converter.toInteger(expectedQuantity));
    }

    public ViewOrderVerification country(String expectedCountryAliasOrValue) {
        var expectedCountry = context.getParamValueOrLiteral(expectedCountryAliasOrValue);
        var actualCountry = response.getCountry();
        assertThat(actualCountry)
                .withFailMessage("Expected country to be '%s', but was '%s'", expectedCountry, actualCountry)
                .isEqualTo(expectedCountry);
        return this;
    }

    public ViewOrderVerification unitPrice(double expectedUnitPrice) {
        var expectedPrice = Converter.toBigDecimal(expectedUnitPrice);
        var actualPrice = response.getUnitPrice();
        assertThat(actualPrice)
                .withFailMessage("Expected unit price to be %s, but was %s", expectedPrice, actualPrice)
                .isEqualByComparingTo(expectedPrice);
        return this;
    }

    public ViewOrderVerification unitPrice(String expectedUnitPrice) {
        return unitPrice(Converter.toDouble(expectedUnitPrice));
    }

    public ViewOrderVerification subtotalPrice(double expectedSubtotalPrice) {
        var expectedPrice = Converter.toBigDecimal(expectedSubtotalPrice);
        var actualPrice = response.getSubtotalPrice();
        assertThat(actualPrice)
                .withFailMessage("Expected subtotal price to be %s, but was %s", expectedPrice, actualPrice)
                .isEqualByComparingTo(expectedPrice);
        return this;
    }

    public ViewOrderVerification subtotalPrice(String expectedSubtotalPrice) {
        return subtotalPrice(Converter.toDouble(expectedSubtotalPrice));
    }

    public ViewOrderVerification subtotalPriceGreaterThanZero() {
        var subtotalPrice = response.getSubtotalPrice();
        assertThat(subtotalPrice)
                .withFailMessage("Subtotal price should be positive, but was: %s", subtotalPrice)
                .isGreaterThan(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderVerification basePrice(BigDecimal expectedBasePrice) {
        var actualBasePrice = response.getBasePrice();
        assertThat(actualBasePrice)
                .withFailMessage("Expected base price to be %s, but was %s", expectedBasePrice, actualBasePrice)
                .isEqualByComparingTo(expectedBasePrice);
        return this;
    }

    public ViewOrderVerification basePrice(double expectedBasePrice) {
        return basePrice(Converter.toBigDecimal(expectedBasePrice));
    }

    public ViewOrderVerification basePrice(String expectedBasePrice) {
        return basePrice(Converter.toBigDecimal(expectedBasePrice));
    }

    public ViewOrderVerification basePriceGreaterThanZero() {
        var basePrice = response.getBasePrice();
        assertThat(basePrice)
                .withFailMessage("Base price should be positive, but was: %s", basePrice)
                .isGreaterThan(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderVerification status(OrderStatus expectedStatus) {
        var actualStatus = response.getStatus();
        assertThat(actualStatus)
                .withFailMessage("Expected status to be %s, but was %s", expectedStatus, actualStatus)
                .isEqualTo(expectedStatus);
        return this;
    }

    public ViewOrderVerification status(String expectedStatus) {
        return status(OrderStatus.valueOf(expectedStatus));
    }


    public ViewOrderVerification discountRate(BigDecimal expectedDiscountRate) {
        var actualDiscountRate = response.getDiscountRate();
        assertThat(actualDiscountRate)
                .withFailMessage("Expected discount rate to be %s, but was %s", expectedDiscountRate, actualDiscountRate)
                .isEqualByComparingTo(expectedDiscountRate);
        return this;
    }


    public ViewOrderVerification discountRate(double expectedDiscountRate) {
        return discountRate(Converter.toBigDecimal(expectedDiscountRate));
    }

    public ViewOrderVerification discountRateGreaterThanOrEqualToZero() {
        var discountRate = response.getDiscountRate();
        assertThat(discountRate)
                .withFailMessage("Discount rate should be non-negative, but was: %s", discountRate)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderVerification discountAmountGreaterThanOrEqualToZero() {
        var discountAmount = response.getDiscountAmount();
        assertThat(discountAmount)
                .withFailMessage("Discount amount should be non-negative, but was: %s", discountAmount)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
        return this;
    }


    public ViewOrderVerification discountAmount(BigDecimal expectedDiscountAmount) {
        var actualDiscountAmount = response.getDiscountAmount();
        assertThat(actualDiscountAmount)
                .withFailMessage("Expected discount amount to be %s, but was %s", expectedDiscountAmount, actualDiscountAmount)
                .isEqualByComparingTo(expectedDiscountAmount);
        return this;
    }

    public ViewOrderVerification discountAmount(double expectedDiscountAmount) {
        return discountAmount(Converter.toBigDecimal(expectedDiscountAmount));
    }

    public ViewOrderVerification discountAmount(String expectedDiscountAmount) {
        return discountAmount(Converter.toBigDecimal(expectedDiscountAmount));
    }

    public ViewOrderVerification appliedCouponCode(String expectedCouponCodeAlias) {
        var expectedCouponCode = context.getParamValue(expectedCouponCodeAlias);
        var actualCouponCode = response.getAppliedCouponCode();
        assertThat(actualCouponCode)
                .withFailMessage("Expected applied coupon code to be '%s', but was '%s'", expectedCouponCode, actualCouponCode)
                .isEqualTo(expectedCouponCode);
        return this;
    }



    public ViewOrderVerification taxRate(BigDecimal expectedTaxRate) {
        var actualTaxRate = response.getTaxRate();
        assertThat(actualTaxRate)
                .withFailMessage("Expected tax rate to be %s, but was %s", expectedTaxRate, actualTaxRate)
                .isEqualByComparingTo(expectedTaxRate);
        return this;
    }

    public ViewOrderVerification taxRate(double expectedTaxRate) {
        return taxRate(Converter.toBigDecimal(expectedTaxRate));
    }

    public ViewOrderVerification taxRate(String expectedTaxRate) {
        return taxRate(Converter.toBigDecimal(expectedTaxRate));
    }

    public ViewOrderVerification taxRateGreaterThanOrEqualToZero() {
        var taxRate = response.getTaxRate();
        assertThat(taxRate)
                .withFailMessage("Tax rate should be non-negative, but was: %s", taxRate)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderVerification taxAmount(BigDecimal expectedTaxAmount) {
        var actualTaxAmount = response.getTaxAmount();
        assertThat(actualTaxAmount)
                .withFailMessage("Expected tax amount to be %s, but was %s", expectedTaxAmount, actualTaxAmount)
                .isEqualByComparingTo(expectedTaxAmount);
        return this;
    }

    public ViewOrderVerification taxAmount(double expectedTaxAmount) {
        return taxAmount(Converter.toBigDecimal(expectedTaxAmount));
    }

    public ViewOrderVerification taxAmount(String expectedTaxAmount) {
        return taxAmount(Converter.toBigDecimal(expectedTaxAmount));
    }

    public ViewOrderVerification taxAmountGreaterThanOrEqualToZero() {
        var taxAmount = response.getTaxAmount();
        assertThat(taxAmount)
                .withFailMessage("Tax amount should be non-negative, but was: %s", taxAmount)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderVerification totalPrice(BigDecimal expectedTotalPrice) {
        var totalPrice = response.getTotalPrice();
        assertThat(totalPrice)
                .withFailMessage("Expected total price to be %s, but was %s", expectedTotalPrice, totalPrice)
                .isEqualTo(expectedTotalPrice);
        return this;
    }

    public ViewOrderVerification totalPrice(double expectedTotalPrice) {
        return totalPrice(Converter.toBigDecimal(expectedTotalPrice));
    }

    public ViewOrderVerification totalPrice(String expectedTotalPrice) {
        return totalPrice(Converter.toBigDecimal(expectedTotalPrice));
    }

    public ViewOrderVerification totalPriceGreaterThanZero() {
        var totalPrice = response.getTotalPrice();
        assertThat(totalPrice)
                .withFailMessage("Total price should be positive, but was: %s", totalPrice)
                .isGreaterThan(BigDecimal.ZERO);
        return this;
    }

    public void orderNumberHasPrefix(String expectedPrefix) {
        var actualOrderNumber = response.getOrderNumber();
        assertThat(actualOrderNumber)
                .withFailMessage("Expected order number to start with '%s', but was: %s", expectedPrefix, actualOrderNumber)
                .startsWith(expectedPrefix);
    }

    public ViewOrderVerification orderTimestamp(Instant expectedTimestamp) {
        var actualTimestamp = response.getOrderTimestamp();
        assertThat(actualTimestamp)
                .withFailMessage("Expected order timestamp to be %s, but was %s", expectedTimestamp, actualTimestamp)
                .isEqualTo(expectedTimestamp);
        return this;
    }

    public ViewOrderVerification orderTimestamp(String expectedTimestamp) {
        return orderTimestamp(Converter.toInstant(expectedTimestamp));
    }

    public ViewOrderVerification orderTimestampIsNotNull() {
        var actualTimestamp = response.getOrderTimestamp();
        assertThat(actualTimestamp)
                .withFailMessage("Expected order timestamp to be set, but was null")
                .isNotNull();
        return this;
    }


    public ViewOrderVerification appliedCouponCodeIsNull() {
        var actualCouponCode = response.getAppliedCouponCode();
        assertThat(actualCouponCode)
                .withFailMessage("Expected applied coupon code to be null, but was '%s'", actualCouponCode)
                .isNull();
        return this;
    }
}

