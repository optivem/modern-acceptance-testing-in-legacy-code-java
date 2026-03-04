package com.optivem.eshop.systemtest.dsl.core.app;

import com.optivem.eshop.systemtest.driver.port.shared.error.ErrorResponse;
import com.optivem.common.Result;

import java.util.function.BiFunction;

import static com.optivem.common.ResultAssert.assertThatResult;

public class UseCaseResult<TSuccessResponse, TSuccessVerification> {
    private final Result<TSuccessResponse, ErrorResponse> result;
    private final UseCaseContext context;
    private final BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> successVerificationFactory;

    public UseCaseResult(
            Result<TSuccessResponse, ErrorResponse> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> successVerificationFactory) {
        this.result = result;
        this.context = context;
        this.successVerificationFactory = successVerificationFactory;
    }

    public TSuccessVerification shouldSucceed() {
        assertThatResult(result).isSuccess();
        return successVerificationFactory.apply(result.getValue(), context);
    }

    public ErrorVerification shouldFail() {
        assertThatResult(result).isFailure();
        return new ErrorVerification(result.getError(), context);
    }
}


