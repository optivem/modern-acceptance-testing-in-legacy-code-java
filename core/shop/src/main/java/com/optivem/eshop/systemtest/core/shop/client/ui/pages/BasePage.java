package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.playwright.PageClient;

import java.util.List;

public abstract class BasePage {
    // Use role='alert' for semantic HTML and accessibility
    private static final String NOTIFICATION_SELECTOR = "[role='alert']";
    private static final String SUCCESS_NOTIFICATION_SELECTOR = "[role='alert'].notification.success";
    private static final String ERROR_NOTIFICATION_SELECTOR = "[role='alert'].notification.error";
    private static final String ERROR_MESSAGE_SELECTOR = "[role='alert'].notification.error .error-message";
    private static final String FIELD_ERROR_SELECTOR = "[role='alert'].notification.error .field-error";
    private static final String NO_NOTIFICATION_ERROR_MESSAGE = "No success or error notification appeared";

    protected final PageClient pageClient;

    public BasePage(PageClient pageClient) {
        this.pageClient = pageClient;
    }

    public boolean hasSuccessNotification() {

        pageClient.waitForVisible(NOTIFICATION_SELECTOR);

        var isSuccess = pageClient.exists(SUCCESS_NOTIFICATION_SELECTOR);

        if(isSuccess) {
            return true;
        }

        var isError = pageClient.exists(ERROR_NOTIFICATION_SELECTOR);

        if(isError) {
            return false;
        }

        throw new RuntimeException(NO_NOTIFICATION_ERROR_MESSAGE);
    }


    public String readSuccessNotification() {
        return pageClient.readTextContent(SUCCESS_NOTIFICATION_SELECTOR);
    }

    public List<String> readErrorNotification() {
        var text = pageClient.readTextContent(ERROR_NOTIFICATION_SELECTOR);
        return text.lines().toList();
    }

    public String readGeneralErrorMessage() {
        return pageClient.readTextContent(ERROR_MESSAGE_SELECTOR);
    }

    public List<String> readFieldErrors() {
        if (!pageClient.exists(FIELD_ERROR_SELECTOR)) {
            return List.of();
        }
        return pageClient.readAllTextContents(FIELD_ERROR_SELECTOR);
    }
}
