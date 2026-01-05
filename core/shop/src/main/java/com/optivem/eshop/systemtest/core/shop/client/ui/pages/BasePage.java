package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.playwright.PageClient;

import java.util.List;

public abstract class BasePage {

    // React uses [role='alert'] directly, without #notifications wrapper
    private static final String NOTIFICATION_SELECTOR = "[role='alert']";
    private static final String SUCCESS_NOTIFICATION_SELECTOR = "[role='alert'].success";
    private static final String ERROR_NOTIFICATION_SELECTOR = "[role='alert'].error";
    private static final String ERROR_MESSAGE_SELECTOR = "[role='alert'].error .error-message";
    private static final String FIELD_ERROR_SELECTOR = "[role='alert'].error .field-error";
    private static final String NO_NOTIFICATION_ERROR_MESSAGE = "No success or error notification appeared";

    protected final PageClient pageClient;

    public BasePage(PageClient pageClient) {
        this.pageClient = pageClient;
    }

    public boolean hasSuccessNotification() {
        // Wait for either success or error notification to appear (not just any alert)
        // Try waiting for success notification first (most common case)
        try {
            pageClient.waitForVisible(SUCCESS_NOTIFICATION_SELECTOR);
            return true;
        } catch (Exception e) {
            // If success notification didn't appear, check for error notification
            try {
                pageClient.waitForVisible(ERROR_NOTIFICATION_SELECTOR);
                return false;
            } catch (Exception e2) {
                throw new RuntimeException(NO_NOTIFICATION_ERROR_MESSAGE);
            }
        }
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
