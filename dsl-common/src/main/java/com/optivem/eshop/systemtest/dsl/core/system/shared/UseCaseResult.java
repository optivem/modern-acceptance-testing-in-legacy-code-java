package com.optivem.eshop.systemtest.dsl.core.system.shared;

import com.optivem.common.Result;

import java.util.function.BiFunction;

import static com.optivem.common.ResultAssert.assertThatResult;

public class UseCaseResult<TSuccessResponse, TFailureResponse, TSuccessVerification, TFailureVerification> {
    private final Result<TSuccessResponse, TFailureResponse> result;
    private final UseCaseContext context;
    private final BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> successVerificationFactory;
    private final BiFunction<TFailureResponse, UseCaseContext, TFailureVerification> failureVerificationFactory;

    public UseCaseResult(
            Result<TSuccessResponse, TFailureResponse> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> successVerificationFactory,
            BiFunction<TFailureResponse, UseCaseContext, TFailureVerification> failureVerificationFactory) {
        this.result = result;
        this.context = context;
        this.successVerificationFactory = successVerificationFactory;
        this.failureVerificationFactory = failureVerificationFactory;
    }

    public TSuccessVerification shouldSucceed() {
        assertThatResult(result).isSuccess();
        return successVerificationFactory.apply(result.getValue(), context);
    }

    public TFailureVerification shouldFail() {
        assertThatResult(result).isFailure();
        return failureVerificationFactory.apply(result.getError(), context);
    }
}


