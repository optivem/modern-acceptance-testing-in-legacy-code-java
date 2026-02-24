package com.optivem.eshop.systemtest.core.shop.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.shop.dtos.errors.SystemError;
import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.UseCaseContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemErrorFailureVerification extends ResponseVerification<SystemError> {

    public SystemErrorFailureVerification(SystemError error, UseCaseContext context) {
        super(error, context);
    }

    public SystemErrorFailureVerification errorMessage(String expectedMessage) {
        var expandedExpectedMessage = getContext().expandAliases(expectedMessage);
        var error = getResponse();
        var errorMessage = error.getMessage();
        assertThat(errorMessage)
                .withFailMessage("Expected error message: '%s', but got: '%s'", expandedExpectedMessage, errorMessage)
                .isEqualTo(expandedExpectedMessage);
        return this;
    }

    public SystemErrorFailureVerification fieldErrorMessage(String expectedField, String expectedMessage) {
        var expandedExpectedField = getContext().expandAliases(expectedField);
        var expandedExpectedMessage = getContext().expandAliases(expectedMessage);
        var error = getResponse();
        var fields = error.getFields();

        assertThat(fields)
                .withFailMessage("Expected field errors but none were found")
                .isNotNull()
                .isNotEmpty();

        var matchingFieldError = fields.stream()
                .filter(f -> expandedExpectedField.equals(f.getField()))
                .findFirst();

        var actualMessage = matchingFieldError
                .map(SystemError.FieldError::getMessage)
                .orElseThrow(() -> new AssertionError(
                        String.format("Expected field error for field '%s', but field was not found in errors: %s",
                                expandedExpectedField, fields)));
        assertThat(actualMessage)
                .withFailMessage("Expected field error message for field '%s': '%s', but got: '%s'",
                        expandedExpectedField, expandedExpectedMessage, actualMessage)
                .isEqualTo(expandedExpectedMessage);

        return this;
    }
}
