package com.optivem.eshop.systemtest.core.shop.driver;

import com.optivem.eshop.systemtest.core.shop.client.commons.Results;
import com.optivem.eshop.systemtest.core.shop.client.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.client.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.client.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.client.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.OrderHistoryPage;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.error.SystemError;
import com.optivem.lang.Result;

import java.util.Objects;


public class ShopUiDriver implements ShopDriver {
    private final ShopUiClient client;

    private HomePage homePage;
    private NewOrderPage newOrderPage;
    private OrderHistoryPage orderHistoryPage;

    private Pages currentPage;

    public ShopUiDriver(String baseUrl) {
        this.client = new ShopUiClient(baseUrl);
    }

    @Override
    public Result<Void, SystemError> goToShop() {
        homePage = client.openHomePage();

        if (!client.isStatusOk() || !client.isPageLoaded()) {
            return Results.failure("Failed to load home page");
        }

        currentPage = Pages.HOME;
        return Results.success();
    }

    @Override
    public Result<PlaceOrderResponse, SystemError> placeOrder(PlaceOrderRequest request) {
        var sku = request.getSku();
        var quantity = request.getQuantity();
        var country = request.getCountry();

        ensureOnNewOrderPage();
        newOrderPage.inputSku(sku);
        newOrderPage.inputQuantity(quantity);
        newOrderPage.inputCountry(country);
        newOrderPage.clickPlaceOrder();

        var isSuccess = newOrderPage.hasSuccessNotification();

        if (!isSuccess) {
            var generalMessage = newOrderPage.readGeneralErrorMessage();
            var fieldErrorTexts = newOrderPage.readFieldErrors();

            if (fieldErrorTexts.isEmpty()) {
                // Business logic error - no field errors
                return Results.failure(generalMessage);
            } else {
                // Validation error with field errors
                // Parse "fieldName: message" format
                var fieldErrors = fieldErrorTexts.stream()
                        .map(text -> {
                            var parts = text.split(":", 2);
                            if (parts.length == 2) {
                                return new SystemError.FieldError(parts[0].trim(), parts[1].trim());
                            }
                            return new SystemError.FieldError("unknown", text);
                        })
                        .toList();

                var error = SystemError.builder()
                        .message(generalMessage)
                        .fields(fieldErrors)
                        .build();

                return Results.failure(error);
            }
        }

        var orderNumberValue = newOrderPage.getOrderNumber();
        var response = PlaceOrderResponse.builder().orderNumber(orderNumberValue).build();
        return Results.success(response);
    }

    @Override
    public Result<GetOrderResponse, SystemError> viewOrder(String orderNumber) {
        ensureOnOrderHistoryPage();
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();

        var isSuccess = orderHistoryPage.hasOrderDetails();

        if (!isSuccess) {
            var errorMessages = orderHistoryPage.readErrorNotification();
            var errorMessage = !errorMessages.isEmpty() ? errorMessages.get(0) : "Order not found";
            return Results.failure(errorMessage);
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

        return Results.success(response);
    }

    @Override
    public Result<Void, SystemError> cancelOrder(String orderNumberAlias) {
        viewOrder(orderNumberAlias);
        orderHistoryPage.clickCancelOrder();

        var cancellationMessage = orderHistoryPage.readSuccessNotification();
        if (!Objects.equals(cancellationMessage, "Order cancelled successfully!")) {
            return Results.failure("Order cancellation failed");
        }

        var displayStatusAfterCancel = orderHistoryPage.getStatus();
        if (!Objects.equals(displayStatusAfterCancel, OrderStatus.CANCELLED)) {
            return Results.failure("Order status not updated to CANCELLED");
        }

        if (!orderHistoryPage.isCancelButtonHidden()) {
            return Results.failure("Cancel button still visible");
        }

        return Results.success();
    }

    @Override
    public void close() {
        client.close();
    }

    private void ensureOnNewOrderPage() {
        if (currentPage != Pages.NEW_ORDER) {
            homePage = client.openHomePage();
            newOrderPage = homePage.clickNewOrder();
            currentPage = Pages.NEW_ORDER;
        }
    }

    private void ensureOnOrderHistoryPage() {
        if (currentPage != Pages.ORDER_HISTORY) {
            homePage = client.openHomePage();
            orderHistoryPage = homePage.clickOrderHistory();
            currentPage = Pages.ORDER_HISTORY;
        }
    }

    private enum Pages {
        NONE,
        HOME,
        NEW_ORDER,
        ORDER_HISTORY
    }
}

