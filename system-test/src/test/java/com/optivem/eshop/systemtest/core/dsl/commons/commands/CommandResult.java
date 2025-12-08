package com.optivem.eshop.systemtest.core.dsl.commons.commands;

import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.results.Result;

import java.util.function.BiFunction;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class CommandResult<TResponse, TSuccessResult> {
    private final Result<TResponse> result;
    private final DslContext context;
    private final BiFunction<TResponse, DslContext, TSuccessResult> successResultFactory;

    public CommandResult(
            Result<TResponse> result,
            DslContext context,
            BiFunction<TResponse, DslContext, TSuccessResult> successResultFactory) {
        this.result = result;
        this.context = context;
        this.successResultFactory = successResultFactory;
    }

    public TSuccessResult expectSuccess() {
        assertThatResult(result).isSuccess();
        return successResultFactory.apply(result.getValue(), context);
    }

    public FailureResult expectFailure() {
        assertThatResult(result).isFailure();
        return new FailureResult(result, context);
    }
}

