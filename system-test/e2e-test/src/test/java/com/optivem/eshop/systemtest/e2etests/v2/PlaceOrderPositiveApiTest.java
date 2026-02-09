package com.optivem.eshop.systemtest.e2etests.v2;

import com.optivem.eshop.systemtest.e2etests.v2.base.BaseE2eTest;
import com.optivem.eshop.systemtest.core.erp.client.dtos.ExtCreateProductRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.optivem.commons.util.ResultAssert.assertThatResult;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.*;
import static org.assertj.core.api.Assertions.assertThat;

class PlaceOrderPositiveApiTest extends BaseE2eTest {

    @Override
    protected void setShopDriver() {
        setUpShopApiClient();
    }

    @Test
    void shouldPlaceOrderWithCorrectSubtotalPrice() {
        // Given
        var sku = createUniqueSku(SKU);
        var createProductRequest = ExtCreateProductRequest.builder()
                .id(sku)
                .title("Test Product")
                .description("Test Description")
                .category("Test Category")
                .brand("Test Brand")
                .price("20.00")
                .build();

        var createProductResult = erpClient.createProduct(createProductRequest);
        assertThatResult(createProductResult).isSuccess();

        // When
        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity("5")
                .country(COUNTRY)
                .build();

        var placeOrderResult = shopApiClient.orders().placeOrder(placeOrderRequest);
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();

        // Then
        var viewOrderResult = shopApiClient.orders().viewOrder(orderNumber);
        assertThatResult(viewOrderResult).isSuccess();

        var order = viewOrderResult.getValue();
        assertThat(order.getSubtotalPrice()).isEqualTo(new BigDecimal("100.00"));
    }

    @Test
    void shouldPlaceOrderWithCorrectSubtotalPriceParameterized() {
        // Given
        var sku = createUniqueSku(SKU);
        var createProductRequest = ExtCreateProductRequest.builder()
                .id(sku)
                .title("Test Product")
                .description("Test Description")
                .category("Test Category")
                .brand("Test Brand")
                .price("15.50")
                .build();

        var createProductResult = erpClient.createProduct(createProductRequest);
        assertThatResult(createProductResult).isSuccess();

        // When
        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity("4")
                .country(COUNTRY)
                .build();

        var placeOrderResult = shopApiClient.orders().placeOrder(placeOrderRequest);
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();

        // Then
        var viewOrderResult = shopApiClient.orders().viewOrder(orderNumber);
        assertThatResult(viewOrderResult).isSuccess();

        var order = viewOrderResult.getValue();
        assertThat(order.getSubtotalPrice()).isEqualTo(new BigDecimal("62.00"));
    }

    @Test
    void shouldPlaceOrder() {
        // Given
        var sku = createUniqueSku(SKU);
        var createProductRequest = ExtCreateProductRequest.builder()
                .id(sku)
                .title("Test Product")
                .description("Test Description")
                .category("Test Category")
                .brand("Test Brand")
                .price("20.00")
                .build();

        var createProductResult = erpClient.createProduct(createProductRequest);
        assertThatResult(createProductResult).isSuccess();

        // When
        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity("5")
                .country(COUNTRY)
                .build();

        var placeOrderResult = shopApiClient.orders().placeOrder(placeOrderRequest);
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();
        assertThat(orderNumber).startsWith("ORD-");

        // Then
        var viewOrderResult = shopApiClient.orders().viewOrder(orderNumber);
        assertThatResult(viewOrderResult).isSuccess();

        var order = viewOrderResult.getValue();
        assertThat(order.getOrderNumber()).isEqualTo(orderNumber);
        assertThat(order.getSku()).isEqualTo(sku);
        assertThat(order.getCountry()).isEqualTo(COUNTRY);
        assertThat(order.getQuantity()).isEqualTo(5);
        assertThat(order.getUnitPrice()).isEqualTo(new BigDecimal("20.00"));
        assertThat(order.getSubtotalPrice()).isEqualTo(new BigDecimal("100.00"));
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PLACED);
        assertThat(order.getDiscountRate()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(order.getDiscountAmount()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(order.getSubtotalPrice()).isGreaterThan(BigDecimal.ZERO);
        assertThat(order.getTaxRate()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(order.getTaxAmount()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(order.getTotalPrice()).isGreaterThan(BigDecimal.ZERO);
    }
}
