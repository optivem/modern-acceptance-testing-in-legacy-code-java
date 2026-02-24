package com.optivem.eshop.systemtest.dsl.core.system.clock.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.clock.ClockDriver;
import com.optivem.eshop.systemtest.driver.api.clock.dtos.error.ClockErrorResponse;
import com.optivem.eshop.systemtest.dsl.core.system.shared.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;

public abstract class BaseClockCommand<TSuccessResponse, TSuccessVerification> extends BaseUseCase<ClockDriver, TSuccessResponse, ClockErrorResponse, TSuccessVerification, ClockErrorVerification> {
    protected BaseClockCommand(ClockDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

