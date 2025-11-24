package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.clients.system.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.OrderHistoryPage;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShopUiDriver implements ShopDriver {
    private final ShopUiClient client;

    private HomePage homePage;
    private NewOrderPage newOrderPage;
    private OrderHistoryPage orderHistoryPage;

    private Pages currentPage;

    private static enum Pages {
        NONE,
        HOME,
        NEW_ORDER,
        ORDER_HISTORY
    }

    public ShopUiDriver(String baseUrl) {
        this.client = new ShopUiClient(baseUrl);
    }

    @Override
    public void goToShop() {
        homePage = client.openHomePage();
        client.assertHomePageLoaded();
        currentPage = Pages.HOME;

        newOrderPage = homePage.clickNewOrder();
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

    @Override
    public Result<String> placeOrder(String sku, String quantity, String country) {

        ensureOnNewOrderPage();
        newOrderPage.inputProductId(sku);
        newOrderPage.inputQuantity(quantity);
        newOrderPage.inputCountry(country);
        newOrderPage.clickPlaceOrder();

        var orderNumberValue = newOrderPage.getOrderNumber();
        if (orderNumberValue.isPresent()) {
            return Result.success(orderNumberValue.get());
        }

        // If no order number, read the error message from the page
        var errorMessage = newOrderPage.readConfirmationMessageText();
        return Result.failure(errorMessage);

//        orderNumberValue.ifPresent(v -> context.results().alias(orderNumberAlias, v));
    }

    @Override
    public void confirmOrderPlaced(String orderNumber, String prefix) {
        newOrderPage.assertConfirmationMessageShown();
        assertTrue(newOrderPage.getOrderNumber().isPresent(), "Order number should be present after placing order");
        assertTrue(newOrderPage.getOriginalPrice().isPresent(), "Original price should be present after placing order");
        assertTrue(newOrderPage.getOriginalPrice().get().compareTo(BigDecimal.ZERO) > 0, "Original price should be positive after placing order");

        var displayOrderNumber = newOrderPage.getOrderNumber();
        assertTrue(displayOrderNumber.isPresent(), "Order number should be present");
        assertTrue(displayOrderNumber.get().startsWith(prefix), "Order number should start with prefix: " + prefix);
    }


    private void viewOrderDetails(String orderNumber) {
        ensureOnOrderHistoryPage();
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();
        orderHistoryPage.waitForOrderDetails();
    }

    @Override
    public void confirmOrderDetails(String orderNumber, Optional<String> sku, Optional<String> quantity, Optional<String> country,
                                    Optional<String> unitPrice, Optional<String> originalPrice,  Optional<String> status) {
        // TODO: VC: If on new order page, we then need to confirm the first original price before going to view the details
//        var originalPrice = newOrderPage.extractOriginalPrice();
//
//        assertEquals(549.75, originalPrice, 0.01, "Original price should be $549.75 (5 × $109.95)");


        viewOrderDetails(orderNumber);

        var displayOrderNumber = orderHistoryPage.getOrderNumber();
        assertEquals(orderNumber, displayOrderNumber, "Should display the order number: " + orderNumber);

        if(sku.isPresent()) {
            var displayProductId = orderHistoryPage.getProductId();
            assertEquals(sku.get(), displayProductId, "Should display product ID: " + sku);
        }

        if(quantity.isPresent()) {
            var displayQuantity = orderHistoryPage.getQuantity();
            assertEquals(quantity.get(), displayQuantity, "Should display quantity: " + quantity);
        }

        if(country.isPresent()) {
            var displayCountry = orderHistoryPage.getCountry();
            assertEquals(country.get(), displayCountry, "Should display country: " + country);
        }

        if(unitPrice.isPresent()) {
            var displayUnitPriceStr = orderHistoryPage.getUnitPrice().toString();
            assertEquals(unitPrice.get(), displayUnitPriceStr, "Should display unit price: " + unitPrice);
        }

        if(originalPrice.isPresent()) {
            var displayOriginalPriceStr = orderHistoryPage.getOriginalPrice().toString();
            assertEquals(originalPrice.get(), displayOriginalPriceStr, "Should display original price: " + originalPrice);
        }

        if(status.isPresent()) {
            var displayStatus = orderHistoryPage.getStatus();
            assertEquals(status.get(), displayStatus, "Should display status: " + status);
        }
    }

    @Override
    public void cancelOrder(String orderNumberAlias) {
        viewOrderDetails(orderNumberAlias);
        orderHistoryPage.clickCancelOrder();
    }

    @Override
    public void confirmOrderCancelled(String orderNumber) {
        var cancellationMessage = orderHistoryPage.getNotificationMessage();
        assertEquals("Order cancelled successfully!", cancellationMessage, "Should display cancellation success message");

        var displayStatusAfterCancel = orderHistoryPage.getStatus();
        assertEquals("CANCELLED", displayStatusAfterCancel, "Status should be CANCELLED after cancellation");
        orderHistoryPage.assertCancelButtonNotVisible();
    }

    @Override
    public void confirmSubtotalPricePositive(String orderNumber) {
        var displayDiscountRate = orderHistoryPage.getDiscountRate();
        var displayDiscountAmount = orderHistoryPage.getDiscountAmount();
        var displaySubtotalPrice = orderHistoryPage.getSubtotalPrice();

        assertTrue(displayDiscountRate.endsWith("%"), "Should display discount rate with % symbol");
        assertTrue(displayDiscountAmount.startsWith("$"), "Should display discount amount with $ symbol");
        assertTrue(displaySubtotalPrice.startsWith("$"), "Should display subtotal price with $ symbol");

        // TODO: VJ: Assert actual values
    }

    @Override
    public void confirmTotalPricePositive(String orderNumber) {
        var displayTaxRate = orderHistoryPage.getTaxRate();
        var displayTaxAmount = orderHistoryPage.getTaxAmount();
        var displayTotalPrice = orderHistoryPage.getTotalPrice();

        assertTrue(displayTaxRate.endsWith("%"), "Should display tax rate with % symbol");
        assertTrue(displayTaxAmount.startsWith("$"), "Should display tax amount with $ symbol");
        assertTrue(displayTotalPrice.startsWith("$"), "Should display total price with $ symbol");

        // TODO: VJ: Assert actual values
    }




    @Override
    public void confirmOrderNumberGeneratedWithPrefix(String orderNumber, String expectedPrefix) {
        // NOTE: VJ: If we are on order creation page, then check order number generated correctly

        viewOrderDetails(orderNumber);

        var displayOrderNumber = orderHistoryPage.getOrderNumber();
        assertEquals(orderNumber, displayOrderNumber, "Should display the order number: " + orderNumber);

        assertThat(displayOrderNumber)
                .withFailMessage("Order number should start with prefix: " + expectedPrefix)
                .startsWith(expectedPrefix);
    }

    @Override
    public void close() {
        client.close();
    }

//    private final ShopUiClient shopUiClient;
//
//    public ShopUiDriver(ShopUiClient shopUiClient) {
//        this.shopUiClient = shopUiClient;
//    }
//
//    public HomePage openHomePage() {
//        return shopUiClient.openHomePage();
//    }
//
//    public void assertHomePageLoaded() {
//        shopUiClient.openHomePage();
//        shopUiClient.assertHomePageLoaded();
//    }
//
//    public String placeOrder(String sku, String quantity, String country) {
//        var homePage = shopUiClient.openHomePage();
//        var newOrderPage = homePage.clickNewOrder();
//
//        newOrderPage.inputProductId(sku);
//        newOrderPage.inputQuantity(quantity);
//        newOrderPage.inputCountry(country);
//        newOrderPage.clickPlaceOrder();
//
//        return newOrderPage.extractOrderNumber();
//    }
//
//    public OrderHistoryPage viewOrderDetails(String orderNumber) {
//        var homePage = shopUiClient.openHomePage();
//        var orderHistoryPage = homePage.clickOrderHistory();
//
//        orderHistoryPage.inputOrderNumber(orderNumber);
//        orderHistoryPage.clickSearch();
//        orderHistoryPage.waitForOrderDetails();
//
//        return orderHistoryPage;
//    }
//
//    public void cancelOrder(String orderNumber) {
//        var orderHistoryPage = viewOrderDetails(orderNumber);
//        orderHistoryPage.clickCancelOrder();
//    }
//
//    public NewOrderPage attemptPlaceOrder(String sku, String quantity, String country) {
//        var homePage = shopUiClient.openHomePage();
//        var newOrderPage = homePage.clickNewOrder();
//
//        newOrderPage.inputProductId(sku);
//        newOrderPage.inputQuantity(quantity);
//        newOrderPage.inputCountry(country);
//        newOrderPage.clickPlaceOrder();
//
//        return newOrderPage;
//    }
//
//    @Override
//    public void close() {
//        if (shopUiClient != null) {
//            shopUiClient.close();
//        }
//    }
}

