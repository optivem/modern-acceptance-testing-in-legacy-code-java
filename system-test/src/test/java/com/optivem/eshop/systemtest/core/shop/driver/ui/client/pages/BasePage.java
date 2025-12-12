package com.optivem.eshop.systemtest.core.shop.driver.ui.client.pages;

import com.optivem.playwright.PageGateway;

import java.util.List;

public abstract class BasePage {

    private static final String NOTIFICATION_SELECTOR = "#notifications .notification";
    private static final String SUCCESS_NOTIFICATION_SELECTOR = "[role='alert'].success";
    private static final String ERROR_NOTIFICATION_SELECTOR = "[role='alert'].error";

    protected final PageGateway pageClient;

    public BasePage(PageGateway pageClient) {
        this.pageClient = pageClient;
    }

    public boolean hasSuccessNotification() {
        pageClient.waitForVisible(NOTIFICATION_SELECTOR);

        if(pageClient.exists(SUCCESS_NOTIFICATION_SELECTOR)) {
            return true;
        }

        if(pageClient.exists(ERROR_NOTIFICATION_SELECTOR)) {
            return false;
        }

        throw new RuntimeException("Notification is neither success nor error");
    }


    public String readSuccessNotification() {
        return pageClient.readTextContent(SUCCESS_NOTIFICATION_SELECTOR);
    }

    public List<String> readErrorNotification() {
        var text = pageClient.readTextContent(ERROR_NOTIFICATION_SELECTOR);
        return text.lines().toList();
    }
}
