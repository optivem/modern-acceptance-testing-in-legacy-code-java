package com.optivem.eshop.systemtest.core.clients.system.ui.pages;

import com.optivem.eshop.systemtest.core.clients.commons.TestPageClient;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewOrderPage {

    private static final String PRODUCT_ID_INPUT_SELECTOR = "[aria-label=\"Product ID\"]";
    private static final String QUANTITY_INPUT_SELECTOR = "[aria-label=\"Quantity\"]";
    private static final String COUNTRY_INPUT_SELECTOR = "[aria-label=\"Country\"]";
    private static final String PLACE_ORDER_BUTTONG_SELECTOR = "[aria-label=\"Place Order\"]";
    private static final String CONFIRMATION_MESSAGE_SELECTOR = "[role='alert']";
    private static final String ORDER_NUMBER_REGEX = "Success! Order has been created with Order Number ([\\w-]+)";
    private static final int ORDER_NUMBER_MATCHER_GROUP = 1;
    private static final String ORIGINAL_PRICE_REGEX = "Original Price \\$(\\d+(?:\\.\\d{2})?)";
    private static final int ORIGINAL_PRICE_MATCHER_GROUP = 1;

    private final TestPageClient pageClient;

    public NewOrderPage(TestPageClient pageClient) {
        this.pageClient = pageClient;
    }

    public void inputProductId(String productId) {
        pageClient.fill(PRODUCT_ID_INPUT_SELECTOR, productId);
    }

    public void inputQuantity(String quantity) {
        pageClient.fill(QUANTITY_INPUT_SELECTOR, quantity);
    }

    public void inputCountry(String country) {
        pageClient.fill(COUNTRY_INPUT_SELECTOR, country);
    }

    public void clickPlaceOrder() {
        pageClient.click(PLACE_ORDER_BUTTONG_SELECTOR);
    }

    public String readConfirmationMessageText() {
        return pageClient.readTextContent(CONFIRMATION_MESSAGE_SELECTOR);
    }

    public String extractOrderNumber() {
        var confirmationMessageText = readConfirmationMessageText();
        var pattern = Pattern.compile(ORDER_NUMBER_REGEX);
        var matcher = pattern.matcher(confirmationMessageText);
        assertTrue(matcher.find(), "Should extract order number from confirmation message: " + confirmationMessageText);
        return matcher.group(ORDER_NUMBER_MATCHER_GROUP);
    }

    public double extractOriginalPrice() {
        var confirmationMessageText = readConfirmationMessageText();
        var pattern = Pattern.compile(ORIGINAL_PRICE_REGEX);
        var matcher = pattern.matcher(confirmationMessageText);
        assertTrue(matcher.find(), "Should extract original price from confirmation message: " + confirmationMessageText);
        return Double.parseDouble(matcher.group(ORIGINAL_PRICE_MATCHER_GROUP));
    }
}

