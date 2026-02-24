package com.optivem.eshop.systemtest.core.clock.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.driver.api.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.commons.dsl.BaseUseCase;
import com.optivem.commons.dsl.UseCaseContext;

public abstract class BaseClockCommand<TSuccessResponse, TSuccessVerification> extends BaseUseCase<ClockDriver, TSuccessResponse, ClockErrorResponse, TSuccessVerification, ClockErrorVerification> {
    protected BaseClockCommand(ClockDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}
