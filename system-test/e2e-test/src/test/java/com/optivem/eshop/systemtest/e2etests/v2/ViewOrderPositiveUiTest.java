package com.optivem.eshop.systemtest.e2etests.v2;

import com.optivem.eshop.systemtest.e2etests.v2.base.BaseE2eTest;
import com.optivem.eshop.systemtest.core.erp.client.dtos.ExtCreateProductRequest;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.optivem.commons.util.ResultAssert.assertThatResult;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.COUNTRY;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.SKU;
import static org.assertj.core.api.Assertions.assertThat;

class ViewOrderPositiveUiTest extends BaseE2eTest {

    @Override
    protected void setShopDriver() {
        setUpShopUiClient();
    }

    @Test
    void shouldViewPlacedOrder() {
        // Given - Create product and place order
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

        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();
        newOrderPage.inputSku(sku);
        newOrderPage.inputQuantity("5");
        newOrderPage.inputCountry(COUNTRY);
        newOrderPage.clickPlaceOrder();

        var placeOrderResult = newOrderPage.getResult();
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = NewOrderPage.getOrderNumber(placeOrderResult.getValue());

        // When - View order using UI pages (navigate back to home first, then to order history)
        var orderHistoryPage = shopUiClient.openHomePage().clickOrderHistory();
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();
        assertThat(orderHistoryPage.isOrderListed(orderNumber)).isTrue();

        var orderDetailsPage = orderHistoryPage.clickViewOrderDetails(orderNumber);

        // Then
        assertThat(orderDetailsPage.getOrderNumber()).isEqualTo(orderNumber);
        assertThat(orderDetailsPage.getSku()).isEqualTo(sku);
        assertThat(orderDetailsPage.getCountry()).isEqualTo(COUNTRY);
        assertThat(orderDetailsPage.getQuantity()).isEqualTo(5);
        assertThat(orderDetailsPage.getUnitPrice()).isEqualTo(new BigDecimal("20.00"));
        assertThat(orderDetailsPage.getSubtotalPrice()).isEqualTo(new BigDecimal("100.00"));
        assertThat(orderDetailsPage.getStatus()).isEqualTo(OrderStatus.PLACED);
        assertThat(orderDetailsPage.getDiscountRate()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(orderDetailsPage.getDiscountAmount()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(orderDetailsPage.getTaxRate()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(orderDetailsPage.getTaxAmount()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(orderDetailsPage.getTotalPrice()).isGreaterThan(BigDecimal.ZERO);
    }
}
