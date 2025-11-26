package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.clients.system.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.OrderHistoryPage;
import com.optivem.eshop.systemtest.core.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.commons.dtos.enums.OrderStatus;
import com.optivem.eshop.systemtest.core.commons.results.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopUiDriver implements ShopDriver {
    private final ShopUiClient client;

    private HomePage homePage;
    private NewOrderPage newOrderPage;
    private OrderHistoryPage orderHistoryPage;

    private Pages currentPage;

    private enum Pages {
        NONE,
        HOME,
        NEW_ORDER,
        ORDER_HISTORY
    }

    public ShopUiDriver(String baseUrl) {
        this.client = new ShopUiClient(baseUrl);
    }

    @Override
    public Result<Void> goToShop() {
        homePage = client.openHomePage();

        if(!client.isStatusOk()) {
            return Result.failure();
        }

        client.assertPageLoaded();
        currentPage = Pages.HOME;
        return Result.success();
    }

    @Override
    public Result<PlaceOrderResponse> placeOrder(String sku, String quantity, String country) {

        ensureOnNewOrderPage();
        newOrderPage.inputSku(sku);
        newOrderPage.inputQuantity(quantity);
        newOrderPage.inputCountry(country);
        newOrderPage.clickPlaceOrder();

        var isSuccess = newOrderPage.hasSuccessNotification();

        if(!isSuccess) {
            var errorMessage = newOrderPage.readErrorNotification();
            return Result.failure(errorMessage);
        }

        var orderNumberValue = newOrderPage.getOrderNumber();
        var response = PlaceOrderResponse.builder().orderNumber(orderNumberValue).build();
        return Result.success(response);
    }

    @Override
    public Result<GetOrderResponse> viewOrder(String orderNumber) {
        ensureOnOrderHistoryPage();
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();

        var isSuccess = orderHistoryPage.hasOrderDetails();

        if(!isSuccess) {
            var errorMessage = orderHistoryPage.readErrorNotification();
            return Result.failure(errorMessage);
        }

        var displayOrderNumber = orderHistoryPage.getOrderNumber();
        var sku = orderHistoryPage.getSku();
        var quantity = orderHistoryPage.getQuantity();
        var country = orderHistoryPage.getCountry();
        var unitPrice = orderHistoryPage.getUnitPrice();
        var originalPrice = orderHistoryPage.getOriginalPrice();
        var discountRate = orderHistoryPage.getDiscountRate();
        var discountAmount = orderHistoryPage.getDiscountAmount();
        var subtotalPrice = orderHistoryPage.getSubtotalPrice();
        var taxRate = orderHistoryPage.getTaxRate();
        var taxAmount = orderHistoryPage.getTaxAmount();
        var totalPrice = orderHistoryPage.getTotalPrice();
        var status = orderHistoryPage.getStatus();

        var response = GetOrderResponse.builder()
                .orderNumber(displayOrderNumber)
                .sku(sku)
                .quantity(Integer.parseInt(quantity))
                .unitPrice(unitPrice)
                .originalPrice(originalPrice)
                .discountRate(discountRate)
                .discountAmount(discountAmount)
                .subtotalPrice(subtotalPrice)
                .taxRate(taxRate)
                .taxAmount(taxAmount)
                .totalPrice(totalPrice)
                .country(country)
                .status(status)
                .build();

        return Result.success(response);
    }

    @Override
    public Result<Void> cancelOrder(String orderNumberAlias) {
        viewOrder(orderNumberAlias);
        orderHistoryPage.clickCancelOrder();

        var cancellationMessage = orderHistoryPage.readSuccessNotification();
        assertEquals("Order cancelled successfully!", cancellationMessage, "Should display cancellation success message");

        var displayStatusAfterCancel = orderHistoryPage.getStatus();
        assertEquals(OrderStatus.CANCELLED, displayStatusAfterCancel, "Status should be CANCELLED after cancellation");
        orderHistoryPage.assertCancelButtonNotVisible();

        return Result.success();
    }

    @Override
    public void close() {
        client.close();
    }

    private void ensureOnNewOrderPage() {
        if(currentPage != Pages.NEW_ORDER) {
            homePage = client.openHomePage();
            newOrderPage = homePage.clickNewOrder();
            currentPage = Pages.NEW_ORDER;
        }
    }

    private void ensureOnOrderHistoryPage() {
        if(currentPage != Pages.ORDER_HISTORY) {
            homePage = client.openHomePage();
            orderHistoryPage = homePage.clickOrderHistory();
            currentPage = Pages.ORDER_HISTORY;
        }
    }
}

