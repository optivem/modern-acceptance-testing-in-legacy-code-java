package com.optivem.eshop.systemtest.core.clients.system.ui.pages;

import com.optivem.eshop.systemtest.core.clients.commons.TestPageClient;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Pattern;

public class NewOrderPage extends BasePage {

    private static final String PRODUCT_ID_INPUT_SELECTOR = "[aria-label=\"Product ID\"]";
    private static final String QUANTITY_INPUT_SELECTOR = "[aria-label=\"Quantity\"]";
    private static final String COUNTRY_INPUT_SELECTOR = "[aria-label=\"Country\"]";
    private static final String PLACE_ORDER_BUTTONG_SELECTOR = "[aria-label=\"Place Order\"]";
    private static final String ORDER_NUMBER_REGEX = "Success! Order has been created with Order Number ([\\w-]+)";
    private static final int ORDER_NUMBER_MATCHER_GROUP = 1;
    private static final String ORIGINAL_PRICE_REGEX = "Original Price \\$(\\d+(?:\\.\\d{2})?)";
    private static final int ORIGINAL_PRICE_MATCHER_GROUP = 1;

    public NewOrderPage(TestPageClient pageClient) {
        super(pageClient);
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

    public Optional<String> getOrderNumber() {
        if(!hasSuccessNotification()) {
            return Optional.empty();
        }

        var confirmationMessageText = readSuccessNotification();

        var pattern = Pattern.compile(ORDER_NUMBER_REGEX);
        var matcher = pattern.matcher(confirmationMessageText);

        if(!matcher.find()) {
            return Optional.empty();
        }

        var result = matcher.group(ORDER_NUMBER_MATCHER_GROUP);
        return Optional.of(result);
    }

    public Optional<BigDecimal> getOriginalPrice() {
        if(!hasSuccessNotification()) {
            return Optional.empty();
        }

        var confirmationMessageText = readSuccessNotification();

        var pattern = Pattern.compile(ORIGINAL_PRICE_REGEX);
        var matcher = pattern.matcher(confirmationMessageText);

        if(!matcher.find()) {
            return Optional.empty();
        }

        var result = BigDecimal.valueOf(Double.parseDouble(matcher.group(ORIGINAL_PRICE_MATCHER_GROUP)));

        return Optional.of(result);
    }
}

