package com.optivem.eshop.systemtest.core.clients.system.ui.pages;

import com.optivem.eshop.systemtest.core.clients.commons.TestPageClient;

public class HomePage {

    private static final String SHOP_BUTTON_SELECTOR = "a[href='/shop.html']";
    private static final String ORDER_HISTORY_BUTTON_SELECTOR = "a[href='/order-history.html']";

    private final TestPageClient pageClient;

    public HomePage(TestPageClient pageClient) {
        this.pageClient = pageClient;
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

