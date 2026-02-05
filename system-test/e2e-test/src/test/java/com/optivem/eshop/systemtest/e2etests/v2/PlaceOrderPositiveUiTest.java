package com.optivem.eshop.systemtest.e2etests.v2;

import com.optivem.eshop.systemtest.base.v2.BaseClientTest;
import com.optivem.eshop.systemtest.core.erp.client.dtos.ExtCreateProductRequest;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.optivem.commons.util.ResultAssert.assertThatResult;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.*;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled("V2 tests disabled for now")
class PlaceOrderPositiveUiTest extends BaseClientTest {

    @BeforeEach
    void setUp() {
        setUpShopUiClient();
        setUpExternalClients();
    }

    @Test
    void shouldPlaceOrderWithCorrectSubtotalPrice() {
        // Given
        var createProductRequest = ExtCreateProductRequest.builder()
                .id(SKU)
                .title("Test Product")
                .description("Test Description")
                .category("Test Category")
                .brand("Test Brand")
                .price("20.00")
                .build();

        var createProductResult = erpClient.createProduct(createProductRequest);
        assertThatResult(createProductResult).isSuccess();

        // When - Place order using UI pages
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();
        newOrderPage.inputSku(SKU);
        newOrderPage.inputQuantity("5");
        newOrderPage.inputCountry(COUNTRY);
        newOrderPage.clickPlaceOrder();

        var placeOrderResult = newOrderPage.getResult();
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = NewOrderPage.getOrderNumber(placeOrderResult.getValue());

        // Then - View order using UI pages
        var orderHistoryPage = homePage.clickOrderHistory();
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();
        assertThat(orderHistoryPage.isOrderListed(orderNumber)).isTrue();

        var orderDetailsPage = orderHistoryPage.clickViewOrderDetails(orderNumber);
        assertThat(orderDetailsPage.getSubtotalPrice()).isEqualTo(new BigDecimal("100.00"));
    }

    @Test
    void shouldPlaceOrder() {
        // Given
        var createProductRequest = ExtCreateProductRequest.builder()
                .id(SKU)
                .title("Test Product")
                .description("Test Description")
                .category("Test Category")
                .brand("Test Brand")
                .price("20.00")
                .build();

        var createProductResult = erpClient.createProduct(createProductRequest);
        assertThatResult(createProductResult).isSuccess();

        // When - Place order using UI pages
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();
        newOrderPage.inputSku(SKU);
        newOrderPage.inputQuantity("5");
        newOrderPage.inputCountry(COUNTRY);
        newOrderPage.clickPlaceOrder();

        var placeOrderResult = newOrderPage.getResult();
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = NewOrderPage.getOrderNumber(placeOrderResult.getValue());
        assertThat(orderNumber).startsWith("ORD-");

        // Then - View order using UI pages
        var orderHistoryPage = homePage.clickOrderHistory();
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();
        assertThat(orderHistoryPage.isOrderListed(orderNumber)).isTrue();

        var orderDetailsPage = orderHistoryPage.clickViewOrderDetails(orderNumber);
        assertThat(orderDetailsPage.getOrderNumber()).isEqualTo(orderNumber);
        assertThat(orderDetailsPage.getSku()).isEqualTo(SKU);
        assertThat(orderDetailsPage.getCountry()).isEqualTo(COUNTRY);
        assertThat(orderDetailsPage.getQuantity()).isEqualTo(5);
        assertThat(orderDetailsPage.getUnitPrice()).isEqualTo(new BigDecimal("20.00"));
        assertThat(orderDetailsPage.getSubtotalPrice()).isEqualTo(new BigDecimal("100.00"));
        assertThat(orderDetailsPage.getStatus()).isEqualTo(OrderStatus.PLACED);
        assertThat(orderDetailsPage.getDiscountRate()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(orderDetailsPage.getDiscountAmount()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(orderDetailsPage.getSubtotalPrice()).isGreaterThan(BigDecimal.ZERO);
        assertThat(orderDetailsPage.getTaxRate()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(orderDetailsPage.getTaxAmount()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(orderDetailsPage.getTotalPrice()).isGreaterThan(BigDecimal.ZERO);
    }
}
