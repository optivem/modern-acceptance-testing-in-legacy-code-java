package com.optivem.eshop.systemtest.e2etests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.eshop.systemtest.e2etests.helpers.ErpApiHelper;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UiE2eTest {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private String baseUrl;
    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        page = browser.newPage();
        baseUrl = TestConfiguration.getBaseUrl();
        httpClient = HttpClient.newHttpClient();
    }

    @AfterEach
    void tearDown() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }

    @Test
    void shouldCalculateOriginalOrderPrice() {
        // Act
        page.navigate(baseUrl + "/shop.html");

        var productIdInput = page.locator("[aria-label='Product ID']");
        productIdInput.fill("HP-15");

        var quantityInput = page.locator("[aria-label='Quantity']");
        quantityInput.fill("5");

        var placeOrderButton = page.locator("[aria-label='Place Order']");
        placeOrderButton.click();

        // Wait for confirmation message to appear
        var confirmationMessage = page.locator("[role='alert']");
        confirmationMessage.waitFor(new Locator.WaitForOptions().setTimeout(TestConfiguration.getWaitSeconds() * 1000));

        var confirmationMessageText = confirmationMessage.textContent();

        var pattern = Pattern.compile("Success! Order has been created with Order Number ([\\w-]+) and Original Price \\$(\\d+(?:\\.\\d{2})?)");
        var matcher = pattern.matcher(confirmationMessageText);

        assertTrue(matcher.find(), "Confirmation message should match expected pattern. Actual: " + confirmationMessageText);

        var originalPriceString = matcher.group(2);
        var originalPrice = Double.parseDouble(originalPriceString);
        assertEquals(549.75, originalPrice, 0.01, "Original price should be $549.75 (5 × $109.95)");
    }

    @Test
    void shouldRetrieveOrderHistory() {
        // Arrange - First place an order to get an order number
        page.navigate(baseUrl + "/shop.html");

        var productIdInput = page.locator("[aria-label='Product ID']");
        productIdInput.fill("SAM-2020");

        var quantityInput = page.locator("[aria-label='Quantity']");
        quantityInput.fill("3");

        var placeOrderButton = page.locator("[aria-label='Place Order']");
        placeOrderButton.click();

        // Wait for confirmation message and extract order number
        var confirmationMessage = page.locator("[role='alert']");
        confirmationMessage.waitFor(new Locator.WaitForOptions().setTimeout(TestConfiguration.getWaitSeconds() * 1000));

        var confirmationMessageText = confirmationMessage.textContent();
        var pattern = Pattern.compile("Success! Order has been created with Order Number ([\\w-]+)");
        var matcher = pattern.matcher(confirmationMessageText);
        assertTrue(matcher.find(), "Should extract order number from confirmation message");
        var orderNumber = matcher.group(1);

        // Act - Navigate to Order History and search for the order
        page.navigate(baseUrl + "/");
        
        var orderHistoryLink = page.locator("a[href='/order-history.html']");
        orderHistoryLink.click();

        var orderNumberInput = page.locator("[aria-label='Order Number']");
        orderNumberInput.fill(orderNumber);

        var searchButton = page.locator("[aria-label='Search']");
        searchButton.click();

        // Wait for order details to appear
        var orderDetails = page.locator("[role='alert']");
        orderDetails.waitFor(new Locator.WaitForOptions().setTimeout(TestConfiguration.getWaitSeconds() * 1000));

        var orderDetailsText = orderDetails.textContent();

        // Assert - Verify order details heading is displayed
        assertTrue(orderDetailsText.contains("Order Details"), "Should display order details heading");

        // Verify order details in read-only textboxes
        var displayOrderNumber = page.locator("[aria-label='Display Order Number']");
        var displayProductId = page.locator("[aria-label='Display Product ID']");
        var displayCountry = page.locator("[aria-label='Display Country']");
        var displayQuantity = page.locator("[aria-label='Display Quantity']");
        var displayUnitPrice = page.locator("[aria-label='Display Unit Price']");
        var displayOriginalPrice = page.locator("[aria-label='Display Original Price']");
        var displayDiscountRate = page.locator("[aria-label='Display Discount Rate']");
        var displayDiscountAmount = page.locator("[aria-label='Display Discount Amount']");
        var displaySubtotalPrice = page.locator("[aria-label='Display Subtotal Price']");
        var displayTaxRate = page.locator("[aria-label='Display Tax Rate']");
        var displayTaxAmount = page.locator("[aria-label='Display Tax Amount']");
        var displayTotalPrice = page.locator("[aria-label='Display Total Price']");

        assertTrue(displayOrderNumber.inputValue().equals(orderNumber), "Should display the order number: " + orderNumber);
        assertTrue(displayProductId.inputValue().equals("SAM-2020"), "Should display SKU SAM-2020");
        assertTrue(displayCountry.inputValue().equals("US"), "Should display country US");
        assertTrue(displayQuantity.inputValue().equals("3"), "Should display quantity 3");
        assertTrue(displayUnitPrice.inputValue().equals("$499.99"), "Should display unit price $499.99");
        assertTrue(displayOriginalPrice.inputValue().equals("$1499.97"), "Should display original price $1499.97");
        assertTrue(displayDiscountRate.inputValue().endsWith("%"), "Should display discount rate with % symbol");
        assertTrue(displayDiscountAmount.inputValue().startsWith("$"), "Should display discount amount with $ symbol");
        assertTrue(displaySubtotalPrice.inputValue().startsWith("$"), "Should display subtotal price with $ symbol");
        assertTrue(displayTaxRate.inputValue().endsWith("%"), "Should display tax rate with % symbol");
        assertTrue(displayTaxAmount.inputValue().startsWith("$"), "Should display tax amount with $ symbol");
        assertTrue(displayTotalPrice.inputValue().startsWith("$"), "Should display total price with $ symbol");
    }

    @Test
    void shouldCancelOrder() {
        // Arrange - First place an order
        page.navigate(baseUrl + "/shop.html");

        var productIdInput = page.locator("[aria-label='Product ID']");
        productIdInput.fill("HUA-P30");

        var quantityInput = page.locator("[aria-label='Quantity']");
        quantityInput.fill("2");

        var placeOrderButton = page.locator("[aria-label='Place Order']");
        placeOrderButton.click();

        // Wait for confirmation message and extract order number
        var confirmationMessage = page.locator("[role='alert']");
        confirmationMessage.waitFor(new Locator.WaitForOptions().setTimeout(TestConfiguration.getWaitSeconds() * 1000));

        var confirmationMessageText = confirmationMessage.textContent();
        assertNotNull(confirmationMessageText);
        assertTrue(confirmationMessageText.startsWith("Success! Order has been created with Order Number"));
        var pattern = Pattern.compile("Success! Order has been created with Order Number ([\\w-]+)");
        var matcher = pattern.matcher(confirmationMessageText);
        assertTrue(matcher.find(), "Should extract order number from confirmation message");
        var orderNumber = matcher.group(1);
        assertNotNull(orderNumber);

        // Act - Navigate to Order History and search for the order
        page.navigate(baseUrl + "/");
        
        var orderHistoryLink = page.locator("a[href='/order-history.html']");
        orderHistoryLink.click();

        var orderNumberInput = page.locator("[aria-label='Order Number']");
        orderNumberInput.fill(orderNumber);

        var searchButton = page.locator("[aria-label='Search']");
        searchButton.click();

        // Wait for order details to appear
        var orderDetails = page.locator("[role='alert']");
        orderDetails.waitFor(new Locator.WaitForOptions().setTimeout(TestConfiguration.getWaitSeconds() * 1000));

        // Verify initial status is PLACED
        var displayStatusBeforeCancel = page.locator("[aria-label='Display Status']");
        assertTrue(displayStatusBeforeCancel.inputValue().equals("PLACED"), "Initial status should be PLACED");

        // Click Cancel Order button
        page.onDialog(dialog -> dialog.accept()); // Auto-accept the alert
        var cancelButton = page.locator("[aria-label='Cancel Order']");
        cancelButton.click();

        // Wait a moment for the order to be cancelled and details refreshed
        page.waitForTimeout(1000);

        // Assert - Verify status changed to CANCELLED
        var displayStatusAfterCancel = page.locator("[aria-label='Display Status']");
        assertTrue(displayStatusAfterCancel.inputValue().equals("CANCELLED"), "Status should be CANCELLED after cancellation");

        // Verify Cancel button is no longer visible (since order is already cancelled)
        var cancelButtonAfter = page.locator("[aria-label='Cancel Order']");
        assertTrue(cancelButtonAfter.count() == 0, "Cancel button should not be visible for cancelled orders");
    }

    @Test
    void shouldRejectOrderWithNonExistentSku() {
        // Act
        page.navigate(baseUrl + "/shop.html");

        var productIdInput = page.locator("[aria-label='Product ID']");
        productIdInput.fill("AUTO-NOTFOUND-999");

        var quantityInput = page.locator("[aria-label='Quantity']");
        quantityInput.fill("5");

        var countryInput = page.locator("[aria-label='Country']");
        countryInput.fill("US");

        var placeOrderButton = page.locator("[aria-label='Place Order']");
        placeOrderButton.click();

        // Wait for error message to appear
        var errorMessage = page.locator("[role='alert']");
        errorMessage.waitFor(new Locator.WaitForOptions().setTimeout(TestConfiguration.getWaitSeconds() * 1000));

        var errorMessageText = errorMessage.textContent();

        // Assert
        assertTrue(errorMessageText.contains("Product does not exist for SKU"),
                "Error message should indicate product does not exist. Actual: " + errorMessageText);
    }

    @Test
    void shouldRejectOrderWithNegativeQuantity() {
        // Act
        page.navigate(baseUrl + "/shop.html");

        var productIdInput = page.locator("[aria-label='Product ID']");
        productIdInput.fill("HP-15");

        var quantityInput = page.locator("[aria-label='Quantity']");
        quantityInput.fill("-5");

        var placeOrderButton = page.locator("[aria-label='Place Order']");
        placeOrderButton.click();

        // Wait for error message to appear
        var errorMessage = page.locator("[role='alert']");
        errorMessage.waitFor(new Locator.WaitForOptions().setTimeout(TestConfiguration.getWaitSeconds() * 1000));

        var errorMessageText = errorMessage.textContent();

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
        // Act
        page.navigate(baseUrl + "/shop.html");

        var productIdInput = page.locator("[aria-label='Product ID']");
        productIdInput.fill(skuValue);

        var quantityInput = page.locator("[aria-label='Quantity']");
        quantityInput.fill("5");

        var placeOrderButton = page.locator("[aria-label='Place Order']");
        placeOrderButton.click();

        // Wait for error message to appear and become visible
        var errorMessage = page.locator("[role='alert']");
        errorMessage.waitFor(new Locator.WaitForOptions()
                .setTimeout(TestConfiguration.getWaitSeconds() * 1000)
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));

        var errorMessageText = errorMessage.textContent();

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
    void shouldRejectOrderWithEmptyQuantity(String quantityValue) throws Exception {
        // Arrange - Set up product in ERP first
        String baseSku = "AUTO-EQ-500";
        BigDecimal unitPrice = new BigDecimal("175.00");

        String sku = setupProductInErp(baseSku, "Test Product", unitPrice);

        // Act
        page.navigate(baseUrl + "/shop.html");

        var productIdInput = page.locator("[aria-label='Product ID']");
        productIdInput.fill(sku);

        var quantityInput = page.locator("[aria-label='Quantity']");
        quantityInput.fill(quantityValue);

        var placeOrderButton = page.locator("[aria-label='Place Order']");
        placeOrderButton.click();

        // Wait for error message to appear and become visible
        var errorMessage = page.locator("[role='alert']");
        errorMessage.waitFor(new Locator.WaitForOptions()
                .setTimeout(TestConfiguration.getWaitSeconds() * 1000)
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));

        var errorMessageText = errorMessage.textContent();

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
        // Act
        page.navigate(baseUrl + "/shop.html");

        var productIdInput = page.locator("[aria-label='Product ID']");
        productIdInput.fill("HP-15");

        var quantityInput = page.locator("[aria-label='Quantity']");
        quantityInput.fill(quantityValue);

        var placeOrderButton = page.locator("[aria-label='Place Order']");
        placeOrderButton.click();

        // Wait for error message to appear
        var errorMessage = page.locator("[role='alert']");
        errorMessage.waitFor(new Locator.WaitForOptions().setTimeout(TestConfiguration.getWaitSeconds() * 1000));

        var errorMessageText = errorMessage.textContent();

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
    void shouldRejectOrderWithEmptyCountry(String countryValue) throws Exception {
        // Arrange - Set up product in ERP first
        String baseSku = "AUTO-EC-700";
        BigDecimal unitPrice = new BigDecimal("245.50");

        String sku = setupProductInErp(baseSku, "Test Product", unitPrice);

        // Act
        page.navigate(baseUrl + "/shop.html");

        var productIdInput = page.locator("[aria-label='Product ID']");
        productIdInput.fill(sku);

        var quantityInput = page.locator("[aria-label='Quantity']");
        quantityInput.fill("5");

        var countryInput = page.locator("[aria-label='Country']");
        countryInput.fill(countryValue);

        var placeOrderButton = page.locator("[aria-label='Place Order']");
        placeOrderButton.click();

        // Wait for error message to appear and become visible
        var errorMessage = page.locator("[role='alert']");
        errorMessage.waitFor(new Locator.WaitForOptions()
                .setTimeout(TestConfiguration.getWaitSeconds() * 1000)
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));

        var errorMessageText = errorMessage.textContent();

        // Assert
        assertTrue(errorMessageText.contains("Country must not be empty"),
                "Error message should be 'Country must not be empty' for country: '" + countryValue + "'. Actual: " + errorMessageText);
    }

    // Helper method to set up product in ERP JSON Server (wrapper for base class method)
    private String setupProductInErp(String baseSku, String title, BigDecimal price) throws Exception {
        return ErpApiHelper.setupProductInErp(httpClient, baseSku, title, price);
    }
}

