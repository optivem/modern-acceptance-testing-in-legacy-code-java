package com.optivem.eshop.systemtest.core.clock.dsl.commands.base;

import com.optivem.eshop.systemtest.core.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.commons.util.Result;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.UseCaseResult;

import java.util.function.BiFunction;

public class ClockUseCaseResult<TSuccessResponse, TSuccessVerification>
        extends UseCaseResult<TSuccessResponse, ClockErrorResponse, TSuccessVerification, ClockErrorVerification> {
    public ClockUseCaseResult(Result<TSuccessResponse, ClockErrorResponse> result,
                              UseCaseContext context,
                              BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, ClockErrorVerification::new);
    }
}