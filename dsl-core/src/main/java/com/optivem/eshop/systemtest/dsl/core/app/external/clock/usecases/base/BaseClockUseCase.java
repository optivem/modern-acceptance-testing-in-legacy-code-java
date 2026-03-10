package com.optivem.eshop.systemtest.dsl.core.app.external.clock.usecases.base;

import com.optivem.eshop.systemtest.driver.port.external.clock.ClockDriver;
import com.optivem.eshop.systemtest.dsl.core.shared.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseContext;

public abstract class BaseClockUseCase<TSuccessResponse, TSuccessVerification> extends BaseUseCase<ClockDriver, TSuccessResponse, TSuccessVerification> {
    protected BaseClockUseCase(ClockDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}
