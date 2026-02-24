package com.optivem.eshop.systemtest.dsl.core.system.clock.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.clock.dtos.error.ClockErrorResponse;
import com.optivem.commons.util.Result;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseResult;

import java.util.function.BiFunction;

public class ClockUseCaseResult<TSuccessResponse, TSuccessVerification>
        extends UseCaseResult<TSuccessResponse, ClockErrorResponse, TSuccessVerification, ClockErrorVerification> {
    public ClockUseCaseResult(Result<TSuccessResponse, ClockErrorResponse> result,
                              UseCaseContext context,
                              BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, ClockErrorVerification::new);
    }
}

