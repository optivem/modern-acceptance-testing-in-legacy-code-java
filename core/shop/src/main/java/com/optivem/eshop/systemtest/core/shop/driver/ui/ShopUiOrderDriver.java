package com.optivem.eshop.systemtest.core.shop.driver.ui;

import com.optivem.eshop.systemtest.core.shop.commons.Results;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.ViewOrderDetailsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.OrderDetailsPage;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.OrderHistoryPage;
import com.optivem.eshop.systemtest.core.shop.driver.OrderDriver;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.lang.Result;

import java.util.Objects;
import java.util.function.Supplier;

public class ShopUiOrderDriver implements OrderDriver {
    private final Supplier<HomePage> homePageSupplier;
    private final PageNavigator pageNavigator;

    private NewOrderPage newOrderPage;
    private OrderHistoryPage orderHistoryPage;
    private OrderDetailsPage orderDetailsPage;

    public ShopUiOrderDriver(Supplier<HomePage> homePageSupplier, PageNavigator pageNavigator) {
        this.homePageSupplier = homePageSupplier;
        this.pageNavigator = pageNavigator;
    }

    @Override
    public Result<PlaceOrderResponse, SystemError> placeOrder(PlaceOrderRequest request) {
        var sku = request.getSku();
        var quantity = request.getQuantity();
        var country = request.getCountry();
        var couponCode = request.getCouponCode();

        ensureOnNewOrderPage();
        newOrderPage.inputSku(sku);
        newOrderPage.inputQuantity(quantity);
        newOrderPage.inputCountry(country);
        newOrderPage.inputCouponCode(couponCode);
        newOrderPage.clickPlaceOrder();

        var isSuccess = newOrderPage.hasSuccessNotification();

        if (!isSuccess) {
            var generalMessage = newOrderPage.readGeneralErrorMessage();
            var fieldErrorTexts = newOrderPage.readFieldErrors();

            if (fieldErrorTexts.isEmpty()) {
                return Results.failure(generalMessage);
            } else {
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
    public Result<ViewOrderDetailsResponse, SystemError> viewOrder(String orderNumber) {
        var result = ensureOnOrderDetailsPage(orderNumber);
        if (result.isFailure()) {
            return Results.failure(result.getError());
        }

        var isSuccess = orderDetailsPage.isLoadedSuccessfully();

        if (!isSuccess) {
            var errorMessages = orderDetailsPage.readErrorNotification();
            var errorMessage = !errorMessages.isEmpty() ? errorMessages.get(0) : "Order not found";
            return Results.failure(errorMessage);
        }

        var displayOrderNumber = orderDetailsPage.getOrderNumber();
        var sku = orderDetailsPage.getSku();
        var quantity = orderDetailsPage.getQuantity();
        var country = orderDetailsPage.getCountry();
        var unitPrice = orderDetailsPage.getUnitPrice();
        var basePrice = orderDetailsPage.getBasePrice();
        var discountRate = orderDetailsPage.getDiscountRate();
        var discountAmount = orderDetailsPage.getDiscountAmount();
        var subtotalPrice = orderDetailsPage.getSubtotalPrice();
        var taxRate = orderDetailsPage.getTaxRate();
        var taxAmount = orderDetailsPage.getTaxAmount();
        var totalPrice = orderDetailsPage.getTotalPrice();
        var status = orderDetailsPage.getStatus();
        var appliedCoupon = orderDetailsPage.getAppliedCoupon();

        var response = ViewOrderDetailsResponse.builder()
                .orderNumber(displayOrderNumber)
                .sku(sku)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .basePrice(basePrice)
                .discountRate(discountRate)
                .discountAmount(discountAmount)
                .subtotalPrice(subtotalPrice)
                .taxRate(taxRate)
                .taxAmount(taxAmount)
                .totalPrice(totalPrice)
                .country(country)
                .status(status)
                .appliedCouponCode(appliedCoupon)
                .build();

        return Results.success(response);
    }

    @Override
    public Result<Void, SystemError> cancelOrder(String orderNumberAlias) {
        viewOrder(orderNumberAlias);
        orderDetailsPage.clickCancelOrder();

        // Check for error notification first
        if (!orderDetailsPage.hasSuccessNotification()) {
            var errorMessage = orderDetailsPage.readGeneralErrorMessage();
            return Results.failure(errorMessage);
        }

        var cancellationMessage = orderDetailsPage.readSuccessNotification();
        if (!Objects.equals(cancellationMessage, "Order cancelled successfully!")) {
            return Results.failure("Order cancellation failed");
        }

        var displayStatusAfterCancel = orderDetailsPage.getStatus();
        if (!Objects.equals(displayStatusAfterCancel, OrderStatus.CANCELLED)) {
            return Results.failure("Order status not updated to CANCELLED");
        }

        if (!orderDetailsPage.isCancelButtonHidden()) {
            return Results.failure("Cancel button still visible");
        }

        return Results.success();
    }

    private void ensureOnNewOrderPage() {
        if (!pageNavigator.isOnPage(PageNavigator.Page.NEW_ORDER)) {
            var homePage = homePageSupplier.get();
            newOrderPage = homePage.clickNewOrder();
            pageNavigator.setCurrentPage(PageNavigator.Page.NEW_ORDER);
        }
    }

    private void ensureOnOrderHistoryPage() {
        if (!pageNavigator.isOnPage(PageNavigator.Page.ORDER_HISTORY)) {
            var homePage = homePageSupplier.get();
            orderHistoryPage = homePage.clickOrderHistory();
            pageNavigator.setCurrentPage(PageNavigator.Page.ORDER_HISTORY);
        }
    }

    private Result<Void, SystemError> ensureOnOrderDetailsPage(String orderNumber) {
        ensureOnOrderHistoryPage();
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();

        // Wait a moment for React to fetch and render the data
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        var isOrderListed = orderHistoryPage.isOrderListed(orderNumber);
        if (!isOrderListed) {
            return Results.failure("Order " + orderNumber + " does not exist.");
        }

        orderDetailsPage = orderHistoryPage.clickViewOrderDetails(orderNumber);
        pageNavigator.setCurrentPage(PageNavigator.Page.ORDER_DETAILS);

        return Results.success();
    }
}

