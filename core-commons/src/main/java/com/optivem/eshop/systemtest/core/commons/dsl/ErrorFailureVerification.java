package com.optivem.eshop.systemtest.core.commons.dsl;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorFailureVerification extends ResponseVerification<Error, UseCaseContext> {

    public ErrorFailureVerification(Error error, UseCaseContext context) {
        super(error, context);
    }

    public ErrorFailureVerification errorMessage(String expectedMessage) {
        var expandedExpectedMessage = getContext().expandAliases(expectedMessage);
        var error = getResponse();
        var errorMessage = error.getMessage();
        assertThat(errorMessage)
                .withFailMessage("Expected error message: '%s', but got: '%s'", expandedExpectedMessage, errorMessage)
                .isEqualTo(expandedExpectedMessage);
        return this;
    }

    public ErrorFailureVerification fieldErrorMessage(String expectedField, String expectedMessage) {
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

        assertThat(matchingFieldError)
                .withFailMessage("Expected field error for field '%s', but field was not found in errors: %s", 
                        expandedExpectedField, fields)
                .isPresent();

        var actualMessage = matchingFieldError.get().getMessage();
        assertThat(actualMessage)
                .withFailMessage("Expected field error message for field '%s': '%s', but got: '%s'", 
                        expandedExpectedField, expandedExpectedMessage, actualMessage)
                .isEqualTo(expandedExpectedMessage);

        return this;
    }
}
