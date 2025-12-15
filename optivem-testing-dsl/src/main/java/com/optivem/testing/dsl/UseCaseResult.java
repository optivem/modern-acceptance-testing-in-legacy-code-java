package com.optivem.testing.dsl;

import com.optivem.lang.Error;
import com.optivem.lang.Result;

import java.util.function.BiFunction;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class UseCaseResult<TResponse, TVerification> {
    private final Result<TResponse, Error> result;
    private final UseCaseContext context;
    private final BiFunction<TResponse, UseCaseContext, TVerification> verificationFactory;

    public UseCaseResult(
            Result<TResponse, Error> result,
            UseCaseContext context,
            BiFunction<TResponse, UseCaseContext, TVerification> verificationFactory) {
        this.result = result;
        this.context = context;
        this.verificationFactory = verificationFactory;
    }

    public TVerification shouldSucceed() {
        assertThatResult(result).isSuccess();
        return verificationFactory.apply(result.getValue(), context);
    }

    public UseCaseFailureVerification shouldFail() {
        assertThatResult(result).isFailure();
        return new UseCaseFailureVerification(result, context);
    }
}

