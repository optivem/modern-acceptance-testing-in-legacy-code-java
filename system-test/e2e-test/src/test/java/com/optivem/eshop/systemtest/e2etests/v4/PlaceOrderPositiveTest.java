package com.optivem.eshop.systemtest.e2etests.v4;

import com.optivem.eshop.systemtest.core.erp.driver.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.e2etests.v4.base.BaseE2eTest;
import com.optivem.testing.Channel;
import com.optivem.testing.DataSource;
import org.junit.jupiter.api.TestTemplate;

import java.math.BigDecimal;

import static com.optivem.commons.util.ResultAssert.assertThatResult;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.*;
import static org.assertj.core.api.Assertions.assertThat;

class PlaceOrderPositiveTest extends BaseE2eTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrderWithCorrectSubtotalPrice() {
        // Given
        var returnsProductRequest = ReturnsProductRequest.builder()
                .sku(SKU)
                .price("20.00")
                .build();

        var returnsProductResult = erpDriver.returnsProduct(returnsProductRequest);
        assertThatResult(returnsProductResult).isSuccess();

        // When
        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku(SKU)
                .quantity("5")
                .country(COUNTRY)
                .build();

        var placeOrderResult = shopDriver.orders().placeOrder(placeOrderRequest);
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();

        // Then
        var viewOrderResult = shopDriver.orders().viewOrder(orderNumber);
        assertThatResult(viewOrderResult).isSuccess();

        var order = viewOrderResult.getValue();
        assertThat(order.getSubtotalPrice()).isEqualTo(new BigDecimal("100.00"));
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"20.00", "5", "100.00"})
    @DataSource({"10.00", "3", "30.00"})
    @DataSource({"15.50", "4", "62.00"})
    @DataSource({"99.99", "1", "99.99"})
    void shouldPlaceOrderWithCorrectSubtotalPriceParameterized(String unitPrice, String quantity, String expectedSubtotalPrice) {
        // Given
        var returnsProductRequest = ReturnsProductRequest.builder()
                .sku(SKU)
                .price(unitPrice)
                .build();

        var returnsProductResult = erpDriver.returnsProduct(returnsProductRequest);
        assertThatResult(returnsProductResult).isSuccess();

        // When
        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku(SKU)
                .quantity(quantity)
                .country(COUNTRY)
                .build();

        var placeOrderResult = shopDriver.orders().placeOrder(placeOrderRequest);
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();

        // Then
        var viewOrderResult = shopDriver.orders().viewOrder(orderNumber);
        assertThatResult(viewOrderResult).isSuccess();

        var order = viewOrderResult.getValue();
        assertThat(order.getSubtotalPrice()).isEqualTo(new BigDecimal(expectedSubtotalPrice));
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrder() {
        // Given
        var returnsProductRequest = ReturnsProductRequest.builder()
                .sku(SKU)
                .price("20.00")
                .build();

        var returnsProductResult = erpDriver.returnsProduct(returnsProductRequest);
        assertThatResult(returnsProductResult).isSuccess();

        // When
        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku(SKU)
                .quantity("5")
                .country(COUNTRY)
                .build();

        var placeOrderResult = shopDriver.orders().placeOrder(placeOrderRequest);
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();
        assertThat(orderNumber).startsWith("ORD-");

        // Then
        var viewOrderResult = shopDriver.orders().viewOrder(orderNumber);
        assertThatResult(viewOrderResult).isSuccess();

        var order = viewOrderResult.getValue();
        assertThat(order.getOrderNumber()).isEqualTo(orderNumber);
        assertThat(order.getSku()).isEqualTo(SKU);
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
