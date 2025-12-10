package com.optivem.testing.dsl;

import com.optivem.results.Result;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class FailureVerification {
    private final Result<?> result;
    private final Context context;

    public FailureVerification(Result<?> result, Context context) {
        this.result = result;
        this.context = context;
    }

    public FailureVerification errorMessage(String expectedMessage) {
        var expandedExpectedMessage = context.expandAliases(expectedMessage);
        var errors = result.getErrors();
        assertThat(errors)
                .withFailMessage("Expected error message: '%s', but got: %s", expandedExpectedMessage, errors)
                .contains(expandedExpectedMessage);
        return this;
    }
}

