package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.eshop.systemtest.core.shop.commons.Results;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.commons.util.Result;
import com.optivem.commons.playwright.PageClient;

import java.util.List;

public abstract class BasePage {
    // Use role='alert' for semantic HTML and accessibility
    private static final String NOTIFICATION_SELECTOR = "[role='alert']";
    private static final String SUCCESS_NOTIFICATION_SELECTOR = "[role='alert'].notification.success";
    private static final String ERROR_NOTIFICATION_SELECTOR = "[role='alert'].notification.error";
    private static final String ERROR_MESSAGE_SELECTOR = "[role='alert'].notification.error .error-message";
    private static final String FIELD_ERROR_SELECTOR = "[role='alert'].notification.error .field-error";
    private static final String NO_NOTIFICATION_ERROR_MESSAGE = "No notification appeared";
    private static final String UNRECOGNIZED_NOTIFICATION_ERROR_MESSAGE = "Notification type is not recognized";

    protected final PageClient pageClient;

    public BasePage(PageClient pageClient) {
        this.pageClient = pageClient;
    }

    private boolean hasSuccessNotification() {

        var hasNotification = pageClient.isVisible(NOTIFICATION_SELECTOR);

        if (!hasNotification) {
            throw new RuntimeException(NO_NOTIFICATION_ERROR_MESSAGE);
        }

        var isSuccess = pageClient.isVisible(SUCCESS_NOTIFICATION_SELECTOR);

        if(isSuccess) {
            return true;
        }

        var isError = pageClient.isVisible(ERROR_NOTIFICATION_SELECTOR);

        if(isError) {
            return false;
        }

        throw new RuntimeException(UNRECOGNIZED_NOTIFICATION_ERROR_MESSAGE);
    }


    private String readSuccessNotification() {
        return pageClient.readTextContent(SUCCESS_NOTIFICATION_SELECTOR);
    }

    private String readGeneralErrorMessage() {
        return pageClient.readTextContent(ERROR_MESSAGE_SELECTOR);
    }

    private List<String> readFieldErrors() {
        if (!pageClient.isVisible(FIELD_ERROR_SELECTOR)) {
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
