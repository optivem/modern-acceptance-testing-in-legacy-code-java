package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.eshop.systemtest.core.shop.commons.Results;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.lang.Result;
import com.optivem.playwright.PageClient;

import java.util.List;
import java.util.function.Supplier;

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

    private boolean hasSuccessNotification() {

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


    private String readSuccessNotification() {
        return pageClient.readTextContent(SUCCESS_NOTIFICATION_SELECTOR);
    }

    private List<String> readErrorNotification() {
        var text = pageClient.readTextContent(ERROR_NOTIFICATION_SELECTOR);
        return text.lines().toList();
    }

    private String readGeneralErrorMessage() {
        pageClient.waitForVisible(ERROR_MESSAGE_SELECTOR);
        return pageClient.readTextContent(ERROR_MESSAGE_SELECTOR);
    }

    private List<String> readFieldErrors() {
        if (!pageClient.exists(FIELD_ERROR_SELECTOR)) {
            return List.of();
        }
        return pageClient.readAllTextContents(FIELD_ERROR_SELECTOR);
    }

    public Result<String, SystemError> getResult() {
        var isSuccess = hasSuccessNotification();

        if (isSuccess) {
            var successMessage = readSuccessNotification();
            return Results.success(successMessage);
        }

        var generalMessage = readGeneralErrorMessage();
        var fieldErrorTexts = readFieldErrors();

        if (fieldErrorTexts.isEmpty()) {
            return Results.failure(generalMessage);
        }

        var fieldErrors = fieldErrorTexts.stream()
                .map(text -> {
                    var parts = text.split(":", 2);
                    if (parts.length == 2) {
                        return new SystemError.FieldError(
                                parts[0].trim(),
                                parts[1].trim()
                        );
                    }
                    return new SystemError.FieldError("unknown", text);
                })
                .toList();

        var error = SystemError.builder()
                .message(generalMessage)
                .fields(fieldErrors)
                .build();

        return Results.failure(error);
    }
}
