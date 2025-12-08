package com.optivem.eshop.systemtest.core.dsl.commons.commands;

import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.results.Result;

import java.util.function.BiFunction;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class CommandResult<TResponse, TVerifications> {
    private final Result<TResponse> result;
    private final DslContext context;
    private final BiFunction<TResponse, DslContext, TVerifications> verificationsFactory;

    public CommandResult(
            Result<TResponse> result,
            DslContext context,
            BiFunction<TResponse, DslContext, TVerifications> verificationsFactory) {
        this.result = result;
        this.context = context;
        this.verificationsFactory = verificationsFactory;
    }

    public TVerifications shouldSucceed() {
        assertThatResult(result).isSuccess();
        return verificationsFactory.apply(result.getValue(), context);
    }

    public FailureVerifications shouldFail() {
        assertThatResult(result).isFailure();
        return new FailureVerifications(result, context);
    }
}

