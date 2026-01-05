package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.playwright.PageClient;

import java.util.regex.Pattern;

public class NewOrderPage extends BasePage {

    private static final String SKU_INPUT_SELECTOR = "[aria-label=\"SKU\"]";
    private static final String QUANTITY_INPUT_SELECTOR = "[aria-label=\"Quantity\"]";
    private static final String COUNTRY_INPUT_SELECTOR = "[aria-label=\"Country\"]";
    private static final String COUPON_CODE_INPUT_SELECTOR = "[aria-label=\"Coupon Code\"]";
    private static final String PLACE_ORDER_BUTTONG_SELECTOR = "[aria-label=\"Place Order\"]";
    private static final String ORDER_NUMBER_REGEX = "Success! Order has been created with Order Number ([\\w-]+)";
    private static final int ORDER_NUMBER_MATCHER_GROUP = 1;
    private static final String ORDER_NUMBER_NOT_FOUND_ERROR = "Could not find order number";

    public NewOrderPage(PageClient pageClient) {
        super(pageClient);
    }

    public void inputSku(String sku) {
        pageClient.setInputValue(SKU_INPUT_SELECTOR, sku);
    }

    public void inputQuantity(String quantity) {
        pageClient.setInputValue(QUANTITY_INPUT_SELECTOR, quantity);
    }

    public void inputCountry(String country) {
        pageClient.setInputValue(COUNTRY_INPUT_SELECTOR, country);
    }

    public void inputCouponCode(String couponCode) {
        pageClient.setInputValue(COUPON_CODE_INPUT_SELECTOR, couponCode);
    }

    public void clickPlaceOrder() {
        pageClient.click(PLACE_ORDER_BUTTONG_SELECTOR);
    }

    public String getOrderNumber() {
        var confirmationMessageText = readSuccessNotification();

        var pattern = Pattern.compile(ORDER_NUMBER_REGEX);
        var matcher = pattern.matcher(confirmationMessageText);

        if (!matcher.find()) {
            throw new RuntimeException(ORDER_NUMBER_NOT_FOUND_ERROR);
        }

        return matcher.group(ORDER_NUMBER_MATCHER_GROUP);
    }
}

