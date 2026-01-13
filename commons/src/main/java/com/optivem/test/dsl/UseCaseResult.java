package com.optivem.test.dsl;

import com.optivem.lang.Result;

import java.util.function.BiFunction;

import static com.optivem.test.assertions.ResultAssert.assertThatResult;

public class UseCaseResult<TSuccessResponse, TFailureResponse, TContext, TSuccessVerification, TFailureVerification> {
    private final Result<TSuccessResponse, TFailureResponse> result;
    private final TContext context;
    private final BiFunction<TSuccessResponse, TContext, TSuccessVerification> verificationFactory;
    private final BiFunction<TFailureResponse, TContext, TFailureVerification> failureVerificationFactory;

    public UseCaseResult(
            Result<TSuccessResponse, TFailureResponse> result,
            TContext context,
            BiFunction<TSuccessResponse, TContext, TSuccessVerification> verificationFactory,
            BiFunction<TFailureResponse, TContext, TFailureVerification> failureVerificationFactory) {
        this.result = result;
        this.context = context;
        this.verificationFactory = verificationFactory;
        this.failureVerificationFactory = failureVerificationFactory;
    }

    public TSuccessVerification shouldSucceed() {
        assertThatResult(result).isSuccess();
        return verificationFactory.apply(result.getValue(), context);
    }

    public TFailureVerification shouldFail() {
        assertThatResult(result).isFailure();
        return failureVerificationFactory.apply(result.getError(), context);
    }
}

