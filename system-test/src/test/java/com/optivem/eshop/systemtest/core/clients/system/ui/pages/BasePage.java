package com.optivem.eshop.systemtest.core.clients.system.ui.pages;

import com.optivem.eshop.systemtest.core.clients.commons.TestPageClient;

import java.util.List;

public abstract class BasePage {

    private static final String SUCCESS_NOTIFICATION_SELECTOR = "[role='alert'].success";
    private static final String ERROR_NOTIFICATION_SELECTOR = "[role='alert'].error";

    protected final TestPageClient pageClient;

    public BasePage(TestPageClient pageClient) {
        this.pageClient = pageClient;
    }

    public Boolean hasSuccessNotification() {
        if(pageClient.exists(SUCCESS_NOTIFICATION_SELECTOR)) {
            return true;
        }
        if(pageClient.exists(ERROR_NOTIFICATION_SELECTOR)) {
            return false;
        }
        return null;
    }

    public String readSuccessNotification() {
        return pageClient.readTextContent(SUCCESS_NOTIFICATION_SELECTOR);
    }

    public List<String> readErrorNotification() {
        var text = pageClient.readTextContent(ERROR_NOTIFICATION_SELECTOR);
        return text.lines().toList();
    }
}
