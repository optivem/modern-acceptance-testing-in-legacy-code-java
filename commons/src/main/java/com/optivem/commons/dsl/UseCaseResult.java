package com.optivem.commons.dsl;

import com.optivem.commons.util.Result;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.optivem.commons.util.ResultAssert.assertThatResult;

public class UseCaseResult<TSuccessResponse, TFailureResponse, TSuccessVerification, TFailureVerification> {
    private final Result<TSuccessResponse, TFailureResponse> result;
    private final UseCaseContext context;
    private final BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory;
    private final BiFunction<TFailureResponse, UseCaseContext, TFailureVerification> failureVerificationFactory;

    public UseCaseResult(
            Result<TSuccessResponse, TFailureResponse> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory,
            BiFunction<TFailureResponse, UseCaseContext, TFailureVerification> failureVerificationFactory) {
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

