package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.commons.util.Result;
import com.optivem.commons.playwright.PageClient;

import java.util.List;

import static com.optivem.eshop.systemtest.core.shop.commons.SystemResults.failure;
import static com.optivem.eshop.systemtest.core.shop.commons.SystemResults.success;

public abstract class BasePage {
    private static final String NOTIFICATION_SELECTOR = "[role='alert']";
    private static final String NOTIFICATION_SUCCESS_SELECTOR = "[role='alert'].notification.success";
    private static final String NOTIFICATION_ERROR_SELECTOR = "[role='alert'].notification.error";
    private static final String NOTIFICATION_ERROR_MESSAGE_SELECTOR = "[role='alert'].notification.error .error-message";
    private static final String NOTIFICATION_ERROR_FIELD_SELECTOR = "[role='alert'].notification.error .field-error";
    private static final String NO_NOTIFICATION_ERROR_MESSAGE = "No notification appeared";
    private static final String UNRECOGNIZED_NOTIFICATION_ERROR_MESSAGE = "Notification type is not recognized";

    protected final PageClient pageClient;

    protected BasePage(PageClient pageClient) {
        this.pageClient = pageClient;
    }

    private boolean hasSuccessNotification() {

        var hasNotification = pageClient.isVisible(NOTIFICATION_SELECTOR);

        if (!hasNotification) {
            throw new IllegalStateException(NO_NOTIFICATION_ERROR_MESSAGE);
        }

        var isSuccess = pageClient.isVisible(NOTIFICATION_SUCCESS_SELECTOR);

        if(isSuccess) {
            return true;
        }

        var isError = pageClient.isVisible(NOTIFICATION_ERROR_SELECTOR);

        if(isError) {
            return false;
        }

        throw new IllegalStateException(UNRECOGNIZED_NOTIFICATION_ERROR_MESSAGE);
    }


    private String readSuccessNotification() {
        return pageClient.readTextContent(NOTIFICATION_SUCCESS_SELECTOR);
    }

    private String readGeneralErrorMessage() {
        return pageClient.readTextContent(NOTIFICATION_ERROR_MESSAGE_SELECTOR);
    }

    private List<String> readFieldErrors() {
        if (!pageClient.isVisible(NOTIFICATION_ERROR_FIELD_SELECTOR)) {
            return List.of();
        }
        return pageClient.readAllTextContents(NOTIFICATION_ERROR_FIELD_SELECTOR);
    }

    public Result<String, SystemError> getResult() {
        var isSuccess = hasSuccessNotification();

        if (isSuccess) {
            var successMessage = readSuccessNotification();
            return success(successMessage);
        }

        var generalMessage = readGeneralErrorMessage();
        var fieldErrorTexts = readFieldErrors();

        if (fieldErrorTexts.isEmpty()) {
            return failure(generalMessage);
        }

        var fieldErrors = fieldErrorTexts.stream()
                .map(text -> {
                    var parts = text.split(":", 2);

                    if(parts.length != 2) {
                        throw new IllegalArgumentException("Invalid field error format: " + text);
                    }

                    return new SystemError.FieldError(
                            parts[0].trim(),
                            parts[1].trim()
                    );
                })
                .toList();

        var error = SystemError.builder()
                .message(generalMessage)
                .fields(fieldErrors)
                .build();

        return failure(error);
    }
}
