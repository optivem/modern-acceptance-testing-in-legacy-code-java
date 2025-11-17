package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.eshop.systemtest.core.clients.ClientFactory;
import com.optivem.eshop.systemtest.core.clients.external.erp.ErpApiClient;
import com.optivem.eshop.systemtest.core.clients.system.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.OrderHistoryPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UiE2eTest {
    
    private ShopUiClient shopUiClient;
    private ErpApiClient erpApiClient;

    @BeforeEach
    void setUp() {
        shopUiClient = ClientFactory.createShopUiClient();
        erpApiClient = ClientFactory.createErpApiClient();
    }

    @AfterEach
    void tearDown() {
        if (shopUiClient != null) {
            shopUiClient.close();
        }

        if (erpApiClient != null) {
            erpApiClient.close();
        }
    }

    @Test
    void shouldCalculateOriginalOrderPrice() {
        // Arrange
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        // Act
        newOrderPage.inputProductId("HP-15");
        newOrderPage.inputQuantity("5");
        newOrderPage.clickPlaceOrder();

        var originalPrice = newOrderPage.extractOriginalPrice();

        // Assert - Use concrete value based on known input (5 × $109.95 = $549.75)
        assertEquals(549.75, originalPrice, 0.01, "Original price should be $549.75 (5 × $109.95)");
    }

    @Test
    void shouldRetrieveOrderHistory() {
        // Arrange - First place an order to get an order number
        var orderNumber = createNewOrder("SAM-2020", "3", "US");

        // Act - Navigate to Order History and search for the order
        var homePage = shopUiClient.openHomePage();
        var orderHistoryPage = homePage.clickOrderHistory();

        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();
        orderHistoryPage.waitForOrderDetails();

        // Assert - Verify all order details fields
        var displayOrderNumber = orderHistoryPage.getOrderNumber();
        var displayProductId = orderHistoryPage.getProductId();
        var displayCountry = orderHistoryPage.getCountry();
        var displayQuantity = orderHistoryPage.getQuantity();
        var displayUnitPrice = orderHistoryPage.getUnitPrice();
        var displayOriginalPrice = orderHistoryPage.getOriginalPrice();
        var displayDiscountRate = orderHistoryPage.getDiscountRate();
        var displayDiscountAmount = orderHistoryPage.getDiscountAmount();
        var displaySubtotalPrice = orderHistoryPage.getSubtotalPrice();
        var displayTaxRate = orderHistoryPage.getTaxRate();
        var displayTaxAmount = orderHistoryPage.getTaxAmount();
        var displayTotalPrice = orderHistoryPage.getTotalPrice();

        // Assert with concrete values where known
        assertEquals(orderNumber, displayOrderNumber, "Should display the order number: " + orderNumber);
        assertEquals("SAM-2020", displayProductId, "Should display SKU SAM-2020");
        assertEquals("US", displayCountry, "Should display country US");
        assertEquals("3", displayQuantity, "Should display quantity 3");
        assertEquals("$499.99", displayUnitPrice, "Should display unit price $499.99");
        assertEquals("$1499.97", displayOriginalPrice, "Should display original price $1499.97 (3 × $499.99)");

        // Assert format for calculated fields
        assertTrue(displayDiscountRate.endsWith("%"), "Should display discount rate with % symbol");
        assertTrue(displayDiscountAmount.startsWith("$"), "Should display discount amount with $ symbol");
        assertTrue(displaySubtotalPrice.startsWith("$"), "Should display subtotal price with $ symbol");
        assertTrue(displayTaxRate.endsWith("%"), "Should display tax rate with % symbol");
        assertTrue(displayTaxAmount.startsWith("$"), "Should display tax amount with $ symbol");
        assertTrue(displayTotalPrice.startsWith("$"), "Should display total price with $ symbol");
    }

    @Test
    void shouldCancelOrder() {
        // Arrange - First place an order
        var orderNumber = createNewOrder("HUA-P30", "2", "US");

        // Navigate to Order History and search for the order
        var orderHistoryPage = viewOrderDetails(orderNumber);

        // Verify initial status is PLACED
        var displayStatusBeforeCancel = orderHistoryPage.getStatus();
        assertEquals("PLACED", displayStatusBeforeCancel, "Initial status should be PLACED");

        // Act - Click Cancel Order button
        orderHistoryPage.clickCancelOrder();

        // Assert - Verify status changed to CANCELLED
        var displayStatusAfterCancel = orderHistoryPage.getStatus();
        assertEquals("CANCELLED", displayStatusAfterCancel, "Status should be CANCELLED after cancellation");

        // Verify Cancel button is no longer visible (since order is already cancelled)
        orderHistoryPage.assertCancelButtonNotVisible();
    }

    @Test
    void shouldRejectOrderWithNonExistentSku() {
        // Arrange
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        // Act
        newOrderPage.inputProductId("AUTO-NOTFOUND-999");
        newOrderPage.inputQuantity("5");
        newOrderPage.inputCountry("US");
        newOrderPage.clickPlaceOrder();

        var errorMessageText = newOrderPage.readConfirmationMessageText();

        // Assert
        assertTrue(errorMessageText.contains("Product does not exist for SKU"),
                "Error message should indicate product does not exist. Actual: " + errorMessageText);
    }

    @Test
    void shouldRejectOrderWithNegativeQuantity() {
        // Arrange
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        // Act
        newOrderPage.inputProductId("HP-15");
        newOrderPage.inputQuantity("-5");
        newOrderPage.clickPlaceOrder();

        var errorMessageText = newOrderPage.readConfirmationMessageText();

        // Assert
        assertTrue(errorMessageText.contains("Quantity must be positive"),
                "Error message should indicate quantity must be positive. Actual: " + errorMessageText);
    }

    private static Stream<Arguments> provideEmptySkuValues() {
        return Stream.of(
                Arguments.of(""),      // Empty string
                Arguments.of("   ")    // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptySkuValues")
    void shouldRejectOrderWithEmptySku(String skuValue) {
        // Arrange
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        // Act
        newOrderPage.inputProductId(skuValue);
        newOrderPage.inputQuantity("5");
        newOrderPage.clickPlaceOrder();

        var errorMessageText = newOrderPage.readConfirmationMessageText();

        // Assert
        assertTrue(errorMessageText.contains("SKU must not be empty"),
                "Error message should be 'SKU must not be empty' for SKU: '" + skuValue + "'. Actual: " + errorMessageText);
    }

    private static Stream<Arguments> provideEmptyQuantityValues() {
        return Stream.of(
                Arguments.of(""),      // Empty string
                Arguments.of("   ")    // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptyQuantityValues")
    void shouldRejectOrderWithEmptyQuantity(String quantityValue) {
        // Arrange - Set up product in ERP first
        var baseSku = "AUTO-EQ-500";
        var unitPrice = new BigDecimal("175.00");

        var sku = setupProductInErp(baseSku, "Test Product", unitPrice);

        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        // Act
        newOrderPage.inputProductId(sku);
        newOrderPage.inputQuantity(quantityValue);
        newOrderPage.clickPlaceOrder();

        var errorMessageText = newOrderPage.readConfirmationMessageText();

        // Assert
        assertTrue(errorMessageText.contains("Quantity must be an integer") || errorMessageText.contains("Quantity must be greater than 0"),
                "Error message should indicate quantity validation error for quantity: '" + quantityValue + "'. Actual: " + errorMessageText);
    }

    private static Stream<Arguments> provideInvalidQuantityValues() {
        return Stream.of(
                Arguments.of("3.5"),   // Decimal value
                Arguments.of("lala")   // String value
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidQuantityValues")
    void shouldRejectOrderWithNonIntegerQuantity(String quantityValue) {
        // Arrange
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        // Act
        newOrderPage.inputProductId("HP-15");
        newOrderPage.inputQuantity(quantityValue);
        newOrderPage.clickPlaceOrder();

        var errorMessageText = newOrderPage.readConfirmationMessageText();

        // Assert
        assertTrue(errorMessageText.contains("Quantity must be an integer"),
                "Error message should be 'Quantity must be an integer' for quantity: " + quantityValue + ". Actual: " + errorMessageText);
    }

    private static Stream<Arguments> provideEmptyCountryValues() {
        return Stream.of(
                Arguments.of(""),      // Empty string
                Arguments.of("   ")    // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptyCountryValues")
    void shouldRejectOrderWithEmptyCountry(String countryValue) {
        // Arrange - Set up product in ERP first
        var baseSku = "AUTO-EC-700";
        var unitPrice = new BigDecimal("245.50");

        var sku = setupProductInErp(baseSku, "Test Product", unitPrice);

        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        // Act
        newOrderPage.inputProductId(sku);
        newOrderPage.inputQuantity("5");
        newOrderPage.inputCountry(countryValue);
        newOrderPage.clickPlaceOrder();

        var errorMessageText = newOrderPage.readConfirmationMessageText();

        // Assert
        assertTrue(errorMessageText.contains("Country must not be empty"),
                "Error message should be 'Country must not be empty' for country: '" + countryValue + "'. Actual: " + errorMessageText);
    }

    // Helper method to set up product in ERP JSON Server
    private String setupProductInErp(String baseSku, String title, BigDecimal price) {
        return erpApiClient.products().create(baseSku, title, price);
    }

    private String createNewOrder(String productId, String quantity, String country) {
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        newOrderPage.inputProductId(productId);
        newOrderPage.inputQuantity(quantity);
        if (country != null && !country.isEmpty()) {
            newOrderPage.inputCountry(country);
        }
        newOrderPage.clickPlaceOrder();

        return newOrderPage.extractOrderNumber();
    }

    private OrderHistoryPage viewOrderDetails(String orderNumber) {
        var homePage = shopUiClient.openHomePage();
        var orderHistoryPage = homePage.clickOrderHistory();

        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();
        orderHistoryPage.waitForOrderDetails();

        return orderHistoryPage;
    }
}

