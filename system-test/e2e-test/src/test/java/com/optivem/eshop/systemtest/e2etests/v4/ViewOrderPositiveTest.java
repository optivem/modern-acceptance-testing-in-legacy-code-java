package com.optivem.eshop.systemtest.e2etests.v4;

import com.optivem.eshop.systemtest.core.erp.driver.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.e2etests.v4.base.BaseE2eTest;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.TestTemplate;

import java.math.BigDecimal;

import static com.optivem.commons.util.ResultAssert.assertThatResult;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.*;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled("V4 tests disabled for now")
class ViewOrderPositiveTest extends BaseE2eTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldViewPlacedOrder() {
        // Given
        var returnsProductRequest = ReturnsProductRequest.builder()
                .sku(SKU)
                .price("25.00")
                .build();

        var returnsProductResult = erpDriver.returnsProduct(returnsProductRequest);
        assertThatResult(returnsProductResult).isSuccess();

        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku(SKU)
                .quantity("4")
                .country(COUNTRY)
                .build();

        var placeOrderResult = shopDriver.orders().placeOrder(placeOrderRequest);
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();

        // When
        var viewOrderResult = shopDriver.orders().viewOrder(orderNumber);

        // Then
        assertThatResult(viewOrderResult).isSuccess();

        var order = viewOrderResult.getValue();
        assertThat(order.getOrderNumber()).isEqualTo(orderNumber);
        assertThat(order.getSku()).isEqualTo(SKU);
        assertThat(order.getCountry()).isEqualTo(COUNTRY);
        assertThat(order.getQuantity()).isEqualTo(4);
        assertThat(order.getUnitPrice()).isEqualTo(new BigDecimal("25.00"));
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
