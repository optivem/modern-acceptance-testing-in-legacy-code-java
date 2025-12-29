package com.optivem.eshop.systemtest.core.shop.dsl.verifications;

import com.optivem.eshop.systemtest.core.shop.client.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewOrderVerification extends ResponseVerification<GetOrderResponse, UseCaseContext> {

    public ViewOrderVerification(GetOrderResponse response, UseCaseContext context) {
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
        return quantity(Integer.parseInt(expectedQuantity));
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
        var expectedPrice = BigDecimal.valueOf(expectedUnitPrice);
        var actualPrice = response.getUnitPrice();
        assertThat(actualPrice)
                .withFailMessage("Expected unit price to be %s, but was %s", expectedPrice, actualPrice)
                .isEqualByComparingTo(expectedPrice);
        return this;
    }

    public ViewOrderVerification unitPrice(String expectedUnitPrice) {
        return unitPrice(Double.parseDouble(expectedUnitPrice));
    }

    public ViewOrderVerification subtotalPrice(double expectedSubtotalPrice) {
        var expectedPrice = BigDecimal.valueOf(expectedSubtotalPrice);
        var actualPrice = response.getSubtotalPrice();
        assertThat(actualPrice)
                .withFailMessage("Expected subtotal price to be %s, but was %s", expectedPrice, actualPrice)
                .isEqualByComparingTo(expectedPrice);
        return this;
    }

    public ViewOrderVerification subtotalPrice(String expectedSubtotalPrice) {
        return subtotalPrice(Double.parseDouble(expectedSubtotalPrice));
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
        return discountRate(BigDecimal.valueOf(expectedDiscountRate));
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
        return discountAmount(BigDecimal.valueOf(expectedDiscountAmount));
    }


    public ViewOrderVerification preTaxTotalGreaterThanZero() {
        var preTaxTotal = response.getPreTaxTotal();
        assertThat(preTaxTotal)
                .withFailMessage("Pre-tax total should be positive, but was: %s", preTaxTotal)
                .isGreaterThan(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderVerification taxRateGreaterThanOrEqualToZero() {
        var taxRate = response.getTaxRate();
        assertThat(taxRate)
                .withFailMessage("Tax rate should be non-negative, but was: %s", taxRate)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
        return this;
    }

    public ViewOrderVerification taxAmountGreaterThanOrEqualToZero() {
        var taxAmount = response.getTaxAmount();
        assertThat(taxAmount)
                .withFailMessage("Tax amount should be non-negative, but was: %s", taxAmount)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
        return this;
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
}

