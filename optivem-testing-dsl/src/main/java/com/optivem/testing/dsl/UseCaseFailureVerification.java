package com.optivem.testing.dsl;

import com.optivem.lang.Error;
import com.optivem.lang.Result;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class UseCaseFailureVerification {
    private final Result<?, Error> result;
    private final UseCaseContext context;

    public UseCaseFailureVerification(Result<?, Error> result, UseCaseContext context) {
        this.result = result;
        this.context = context;
    }

    public UseCaseFailureVerification errorMessage(String expectedMessage) {
        var expandedExpectedMessage = context.expandAliases(expectedMessage);
        Error error = result.getError();
        var errorMessage = error.getMessage();
        assertThat(errorMessage)
                .withFailMessage("Expected error message: '%s', but got: '%s'", expandedExpectedMessage, errorMessage)
                .isEqualTo(expandedExpectedMessage);
        return this;
    }

    public UseCaseFailureVerification fieldErrorMessage(String expectedField, String expectedMessage) {
        var expandedExpectedField = context.expandAliases(expectedField);
        var expandedExpectedMessage = context.expandAliases(expectedMessage);
        Error error = result.getError();
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

