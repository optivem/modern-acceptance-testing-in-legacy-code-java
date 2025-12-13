package com.optivem.eshop.systemtest.core.shop.driver.ui;

import com.optivem.eshop.systemtest.core.shop.driver.dtos.requests.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.driver.ui.client.ShopUiClient;
import com.optivem.eshop.systemtest.core.shop.driver.ui.client.pages.HomePage;
import com.optivem.eshop.systemtest.core.shop.driver.ui.client.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.shop.driver.ui.client.pages.OrderHistoryPage;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.enums.OrderStatus;
import com.optivem.lang.Error;
import com.optivem.lang.Result;
import com.optivem.lang.Results;
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
    public Result<Void, Error> goToShop() {
        homePage = client.openHomePage();

        if(!client.isStatusOk() || !client.isPageLoaded()) {
            return Results.failure("Failed to load home page");
        }

        currentPage = Pages.HOME;
        return Results.success();
    }

    @Override
    public Result<PlaceOrderResponse, Error> placeOrder(PlaceOrderRequest request) {
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
            var errorMessages = newOrderPage.readErrorNotification();
            
            if (errorMessages.isEmpty()) {
                return Results.failure("Order placement failed");
            }
            
            var firstMessage = errorMessages.get(0);
            
            // Distinguish between validation errors and business logic errors
            if (isValidationError(firstMessage)) {
                // Validation errors: return generic message + field errors
                var fieldErrors = errorMessages.stream()
                        .map(msg -> new Error.FieldError(extractFieldName(msg), msg))
                        .toList();
                
                var error = Error.builder()
                        .message("The request contains one or more validation errors")
                        .fields(fieldErrors)
                        .build();
                
                return Results.failure(error);
            } else {
                // Business logic errors: return specific message directly
                return Results.failure(firstMessage);
            }
        }

        var orderNumberValue = newOrderPage.getOrderNumber();
        var response = PlaceOrderResponse.builder().orderNumber(orderNumberValue).build();
        return Results.success(response);
    }

    @Override
    public Result<GetOrderResponse, Error> viewOrder(String orderNumber) {
        ensureOnOrderHistoryPage();
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();

        var isSuccess = orderHistoryPage.hasOrderDetails();

        if(!isSuccess) {
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
    public Result<Void, Error> cancelOrder(String orderNumberAlias) {
        viewOrder(orderNumberAlias);
        orderHistoryPage.clickCancelOrder();

        var cancellationMessage = orderHistoryPage.readSuccessNotification();
        if(!Objects.equals(cancellationMessage, "Order cancelled successfully!")) {
            return Results.failure("Order cancellation failed");
        }

        var displayStatusAfterCancel = orderHistoryPage.getStatus();
        if(!Objects.equals(displayStatusAfterCancel, OrderStatus.CANCELLED)) {
            return Results.failure("Order status not updated to CANCELLED");
        }

        if(!orderHistoryPage.isCancelButtonHidden()) {
            return Results.failure("Cancel button still visible");
        }

        return Results.success();
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
    
    /**
     * Determine if an error message is a validation error vs business logic error.
     * Validation errors contain keywords like "must", "required", "cannot", "invalid".
     * Business logic errors contain domain-specific messages.
     */
    private boolean isValidationError(String errorMessage) {
        if (errorMessage == null || errorMessage.isEmpty()) {
            return false;
        }
        
        var lowerMessage = errorMessage.toLowerCase();
        
        // Validation error patterns
        return lowerMessage.contains("must") || 
               lowerMessage.contains("required") || 
               lowerMessage.contains("cannot") || 
               lowerMessage.contains("invalid") ||
               lowerMessage.contains("should");
    }
    
    /**
     * Extract field name from error message.
     * E.g., "Quantity must be positive" -> "quantity"
     *       "SKU must not be empty" -> "sku"
     *       "Country must not be empty" -> "country"
     */
    private String extractFieldName(String errorMessage) {
        if (errorMessage == null || errorMessage.isEmpty()) {
            return "unknown";
        }
        
        var lowerMessage = errorMessage.toLowerCase();
        
        // Try to match common patterns: "Quantity must...", "SKU must...", etc.
        if (lowerMessage.startsWith("quantity")) {
            return "quantity";
        } else if (lowerMessage.startsWith("sku")) {
            return "sku";
        } else if (lowerMessage.startsWith("country")) {
            return "country";
        }
        
        // Fallback: extract first word and lowercase it
        var firstWord = errorMessage.split("\\s+")[0];
        return firstWord.toLowerCase();
    }
}

