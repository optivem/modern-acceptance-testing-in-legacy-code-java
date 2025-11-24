package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.clients.system.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.OrderStatus;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ShopApiDriver implements ShopDriver {
    private final ShopApiClient apiClient;

    private final HashMap<String, HttpResponse<String>> ordersPlaced;
    private final HashMap<String, HttpResponse<String>> ordersViewed;
    private final HashMap<String, HttpResponse<String>> ordersCancelled;

    public ShopApiDriver(String baseUrl) {
        this.apiClient = new ShopApiClient(baseUrl);
        this.ordersPlaced = new HashMap<>();
        this.ordersViewed = new HashMap<>();
        this.ordersCancelled = new HashMap<>();
    }

    @Override
    public void goToShop() {
        var httpResponse = apiClient.echo().echo();
        apiClient.echo().assertEchoSuccessful(httpResponse);
    }

    @Override
    public Result<String> placeOrder(String sku, String quantity, String country) {

        var httpResponse = apiClient.orders().placeOrder(sku, quantity, country);
//        registerOrderResponse(ordersPlaced, orderNumberAlias, httpResponse);

        var orderNumber = apiClient.orders().getOrderNumberIfOrderPlacedSuccessfully(httpResponse);

        if(orderNumber.isPresent()) {
            return Result.success(orderNumber.get());
        }

        var errorMessage = apiClient.getErrorMessage(httpResponse);
        return Result.failure(errorMessage);
    }

    @Override
    public void confirmOrderPlaced(String orderNumberAlias, String prefix) {
        var httpResponse = ordersPlaced.get(orderNumberAlias);
        var response = apiClient.orders().assertOrderPlacedSuccessfully(httpResponse);

        assertNotNull(response.getOrderNumber(), "Order number should be not be null");
        assertFalse(response.getOrderNumber().isEmpty(), "Order number should be not be empty");
        assertTrue(response.getOrderNumber().startsWith(prefix), "Order number should start with prefix: " + prefix);
    }

//    // TODO: VJ: Consider deleting
//    @Override
//    public void viewOrderDetails(String orderNumberAlias) {
//        var orderNumberValue = context.results().alias(orderNumberAlias);
//        var
//        registerOrderResponse(ordersViewed, orderNumberAlias, httpResponse);
//    }

    @Override
    public void confirmOrderDetails(String orderNumber, Optional<String> sku, Optional<String> quantity, Optional<String> country,
                                    Optional<String> unitPrice, Optional<String> originalPrice,  Optional<String> status) {
        var httpResponse = apiClient.orders().viewOrder(orderNumber);
        var response = apiClient.orders().assertOrderViewedSuccessfully(httpResponse);

        // TODO: VJ: Confirm the order number value too

        if(sku.isPresent()) {
            assertEquals(sku.get(), response.getSku());
        }

        if(quantity.isPresent()) {
            assertEquals(quantity.get(), String.valueOf(response.getQuantity()));
        }

        if(country.isPresent()) {
            assertEquals(country.get(), response.getCountry());
        }

        if(unitPrice.isPresent()) {
            assertEquals(unitPrice.get(), String.valueOf(response.getUnitPrice()));
        }

        if(originalPrice.isPresent()) {
            assertEquals(originalPrice.get(), String.valueOf(response.getOriginalPrice()));
        }

        if(status.isPresent()) {
            assertEquals(OrderStatus.valueOf(status.get()), response.getStatus(), "Order status should match");
        }

        var totalPrice = response.getTotalPrice();
        assertNotNull(totalPrice, "Total price should not be null");
        assertTrue(totalPrice.compareTo(BigDecimal.ZERO) > 0, "Total price should be positive");
    }

    @Override
    public void confirmSubtotalPricePositive(String orderNumber) {
        var response = viewOrderDetailsSuccessfully(orderNumber);

        var discountRate = response.getDiscountRate();
        var discountAmount = response.getDiscountAmount();
        var subtotalPrice = response.getSubtotalPrice();

        assertThat(discountRate)
                .withFailMessage("Discount rate should be non-negative")
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);

        assertThat(discountAmount)
                .withFailMessage("Discount amount should be non-negative")
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);

        assertThat(subtotalPrice)
                .withFailMessage("Subtotal price should be positive")
                .isGreaterThan(BigDecimal.ZERO);
    }

    private GetOrderResponse viewOrderDetailsSuccessfully(String orderNumber) {
        var httpResponse = apiClient.orders().viewOrder(orderNumber);
        return apiClient.orders().assertOrderViewedSuccessfully(httpResponse);
    }

    @Override
    public void confirmTotalPricePositive(String orderNumber) {
        var response = viewOrderDetailsSuccessfully(orderNumber);

        var taxRate = response.getTaxRate();
        var taxAmount = response.getTaxAmount();
        var totalPrice = response.getTotalPrice();

        assertThat(taxRate)
                .withFailMessage("Tax rate should be non-negative")
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);

        assertThat(taxAmount)
                .withFailMessage("Tax amount should be non-negative")
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);

        assertThat(totalPrice)
                .withFailMessage("Total price should be positive")
                .isGreaterThan(BigDecimal.ZERO);
    }

    @Override
    public void confirmOrderNumberGeneratedWithPrefix(String orderNumber, String expectedPrefix) {
        // TODO: VJ: Check the generated order number prefix when placing the order

        assertThat(orderNumber)
                .withFailMessage("Order number should start with prefix: " + expectedPrefix)
                .startsWith(expectedPrefix);

        // TODO: VJ: Can call order details?
    }

    @Override
    public void cancelOrder(String orderNumber) {
        var httpResponse = apiClient.orders().cancelOrder(orderNumber);
        registerOrderResponse(ordersCancelled, orderNumber, httpResponse);
    }

    @Override
    public void confirmOrderCancelled(String orderNumber) {
        var httpResponse = ordersCancelled.get(orderNumber);
        apiClient.orders().assertOrderCancelledSuccessfully(httpResponse);
    }

    private static void registerOrderResponse(HashMap<String, HttpResponse<String>> map, String orderNumber, HttpResponse<String> httpResponse) {
        if(map.containsKey(orderNumber)) {
            throw new IllegalStateException("Response for order number " + orderNumber + " is already registered.");
        }

        map.put(orderNumber, httpResponse);
    }

    @Override
    public void close() {
        apiClient.close();
    }

//    private final ShopApiClient shopApiClient;
//
//    public ShopApiDriver(ShopApiClient shopApiClient) {
//        this.shopApiClient = shopApiClient;
//    }
//
//    public void assertEchoSuccessful() {
//        var httpResponse = shopApiClient.echo().echo();
//        shopApiClient.echo().assertEchoSuccessful(httpResponse);
//    }
//
//    public String placeOrder(String sku, int quantity, String country) {
//        var httpResponse = shopApiClient.orders().placeOrder(sku, String.valueOf(quantity), country);
//        var response = shopApiClient.orders().assertOrderPlacedSuccessfully(httpResponse);
//        return response.getOrderNumber();
//    }
//
//    public GetOrderResponse getOrderDetails(String orderNumber) {
//        var httpResponse = shopApiClient.orders().viewOrder(orderNumber);
//        return shopApiClient.orders().assertOrderViewedSuccessfully(httpResponse);
//    }
//
//    public void cancelOrder(String orderNumber) {
//        var httpResponse = shopApiClient.orders().cancelOrder(orderNumber);
//        shopApiClient.orders().assertOrderCancelledSuccessfully(httpResponse);
//    }
//
//    public HttpResponse<String> attemptPlaceOrder(String sku, String quantity, String country) {
//        return shopApiClient.orders().placeOrder(sku, quantity, country);
//    }
//
//    public void assertOrderPlacementFailed(HttpResponse<String> httpResponse) {
//        shopApiClient.orders().assertOrderPlacementFailed(httpResponse);
//    }
//
//    public String getOrderPlacementErrorMessage(HttpResponse<String> httpResponse) {
//        return shopApiClient.orders().getErrorMessage(httpResponse);
//    }
//
//    @Override
//    public void close() {
//        if (shopApiClient != null) {
//            shopApiClient.close();
//        }
//    }
}

