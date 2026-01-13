package com.optivem.eshop.systemtest.core.clock.dsl.commands.base;

import com.optivem.eshop.systemtest.core.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.test.dsl.ResponseVerification;
import com.optivem.test.dsl.UseCaseContext;

public class ClockErrorVerification extends ResponseVerification<ClockErrorResponse, UseCaseContext> {
    public ClockErrorVerification(ClockErrorResponse clockErrorResponse, UseCaseContext useCaseContext) {
        super(clockErrorResponse, useCaseContext);
    }
}
