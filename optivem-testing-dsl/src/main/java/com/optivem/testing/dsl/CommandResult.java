package com.optivem.testing.dsl;

import com.optivem.results.Result;

import java.util.function.BiFunction;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class CommandResult<TResponse, TVerification> {
    private final Result<TResponse> result;
    private final Context context;
    private final BiFunction<TResponse, Context, TVerification> verificationFactory;

    public CommandResult(
            Result<TResponse> result,
            Context context,
            BiFunction<TResponse, Context, TVerification> verificationFactory) {
        this.result = result;
        this.context = context;
        this.verificationFactory = verificationFactory;
    }

    public TVerification shouldSucceed() {
        assertThatResult(result).isSuccess();
        return verificationFactory.apply(result.getValue(), context);
    }

    public FailureVerification shouldFail() {
        assertThatResult(result).isFailure();
        return new FailureVerification(result, context);
    }
}

