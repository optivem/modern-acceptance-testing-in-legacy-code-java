package com.optivem.eshop.systemtest.core.shop.driver.ui;

import com.optivem.eshop.systemtest.core.shop.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.driver.ui.client.ShopUiClient;
import com.optivem.eshop.systemtest.core.shop.driver.ui.client.pages.HomePage;
import com.optivem.eshop.systemtest.core.shop.driver.ui.client.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.shop.driver.ui.client.pages.OrderHistoryPage;
import com.optivem.eshop.systemtest.core.shop.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.dtos.enums.OrderStatus;
import com.optivem.results.Result;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;

import java.util.Objects;


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

        if(!client.isStatusOk() || !client.isPageLoaded()) {
            return Result.failure();
        }

        currentPage = Pages.HOME;
        return Result.success();
    }

    @Override
    public Result<PlaceOrderResponse> placeOrder(PlaceOrderRequest request) {
        var sku = request.getSku();
        var quantity = request.getQuantity();
        var country = request.getCountry();

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
                .quantity(quantity)
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
        if(!Objects.equals(cancellationMessage, "Order cancelled successfully!")) {
            return Result.failure();
        }

        var displayStatusAfterCancel = orderHistoryPage.getStatus();
        if(!Objects.equals(displayStatusAfterCancel, OrderStatus.CANCELLED)) {
            return Result.failure();
        }

        if(!orderHistoryPage.isCancelButtonHidden()) {
            return Result.failure();
        }

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

