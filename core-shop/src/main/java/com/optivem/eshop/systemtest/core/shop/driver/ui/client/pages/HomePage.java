package com.optivem.eshop.systemtest.core.shop.driver.ui.client.pages;

import com.optivem.playwright.PageClient;

public class HomePage extends BasePage {

    private static final String SHOP_BUTTON_SELECTOR = "a[href='/shop.html']";
    private static final String ORDER_HISTORY_BUTTON_SELECTOR = "a[href='/order-history.html']";

    public HomePage(PageClient pageClient) {
        super(pageClient);
    }

    public NewOrderPage clickNewOrder() {
        pageClient.click(SHOP_BUTTON_SELECTOR);
        return new NewOrderPage(pageClient);
    }

    public OrderHistoryPage clickOrderHistory() {
        pageClient.click(ORDER_HISTORY_BUTTON_SELECTOR);
        return new OrderHistoryPage(pageClient);
    }
}

