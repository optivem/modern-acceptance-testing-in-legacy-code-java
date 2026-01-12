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
        pageClient.waitForVisible(ERROR_MESSAGE_SELECTOR);
        return pageClient.readTextContent(ERROR_MESSAGE_SELECTOR);
    }

    public List<String> readFieldErrors() {
        if (!pageClient.exists(FIELD_ERROR_SELECTOR)) {
            return List.of();
        }
        return pageClient.readAllTextContents(FIELD_ERROR_SELECTOR);
    }

    /**
     * Checks if the page operation succeeded and returns a Result based on notification state.
     * @param successValueSupplier Supplier that provides the success value (only called if operation succeeded)
     * @return Success result with value, or failure result with error details
     */
    public <T> Result<T, SystemError> getResult(Supplier<T> successValueSupplier) {
        var isSuccess = hasSuccessNotification();

        if (isSuccess) {
            return Results.success(successValueSupplier.get());
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

    /**
     * Checks if the page operation succeeded and returns a Result for void operations.
     * @return Success result, or failure result with error details
     */
    public Result<Void, SystemError> getResult() {
        return getResult(() -> null);
    }
}
