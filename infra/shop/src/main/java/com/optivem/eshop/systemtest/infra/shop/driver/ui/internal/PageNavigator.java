package com.optivem.eshop.systemtest.infra.shop.driver.ui.internal;

public class PageNavigator {
    private Page currentPage = Page.NONE;

    public boolean isOnPage(Page page) {
        return currentPage == page;
    }

    public void setCurrentPage(Page page) {
        this.currentPage = page;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public enum Page {
        NONE,
        HOME,
        NEW_ORDER,
        ORDER_HISTORY,
        ORDER_DETAILS,
        COUPON_MANAGEMENT
    }
}

