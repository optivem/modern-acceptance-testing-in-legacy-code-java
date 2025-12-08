package com.optivem.eshop.systemtest.core.dsl.commons.commands;

import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.results.Result;

import static org.assertj.core.api.Assertions.assertThat;

public class FailureResult {
    private final Result<?> result;
    private final DslContext context;

    public FailureResult(Result<?> result, DslContext context) {
        this.result = result;
        this.context = context;
    }

    public FailureResult expectErrorMessage(String expectedMessage) {
        // Replace all aliases in the expected message with their actual generated values
        var expandedExpectedMessage = expectedMessage;
        var aliases = context.params().getAllAliases();
        for (var entry : aliases.entrySet()) {
            var alias = entry.getKey();
            var actualValue = entry.getValue();
            expandedExpectedMessage = expandedExpectedMessage.replace(alias, actualValue);
        }

        var errors = result.getErrors();
        var finalExpectedMessage = expandedExpectedMessage;
        assertThat(errors)
                .withFailMessage("Expected error message: '%s', but got: %s", finalExpectedMessage, errors)
                .contains(finalExpectedMessage);
        return this;
    }
}

