package com.optivem.eshop.systemtest.core.shop.driver.ui.client.pages;

import com.optivem.playwright.PageClient;

import java.util.regex.Pattern;

public class NewOrderPage extends BasePage {

    private static final String SKU_INPUT_SELECTOR = "[aria-label=\"SKU\"]";
    private static final String QUANTITY_INPUT_SELECTOR = "[aria-label=\"Quantity\"]";
    private static final String COUNTRY_INPUT_SELECTOR = "[aria-label=\"Country\"]";
    private static final String PLACE_ORDER_BUTTONG_SELECTOR = "[aria-label=\"Place Order\"]";
    private static final String ORDER_NUMBER_REGEX = "Success! Order has been created with Order Number ([\\w-]+)";
    private static final int ORDER_NUMBER_MATCHER_GROUP = 1;

    public NewOrderPage(PageClient pageClient) {
        super(pageClient);
    }

    public void inputSku(String sku) {
        pageClient.fill(SKU_INPUT_SELECTOR, sku);
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

    public String getOrderNumber() {
        var confirmationMessageText = readSuccessNotification();

        var pattern = Pattern.compile(ORDER_NUMBER_REGEX);
        var matcher = pattern.matcher(confirmationMessageText);

        if(!matcher.find()) {
            throw new RuntimeException("Could not find order number");
        }

        return matcher.group(ORDER_NUMBER_MATCHER_GROUP);
    }
}

