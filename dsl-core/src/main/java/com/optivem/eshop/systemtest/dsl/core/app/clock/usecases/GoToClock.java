package com.optivem.eshop.systemtest.dsl.core.app.clock.usecases;

import com.optivem.eshop.systemtest.driver.port.clock.ClockDriver;
import com.optivem.eshop.systemtest.dsl.core.app.clock.usecases.base.BaseClockUseCase;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.app.VoidVerification;

public class GoToClock extends BaseClockUseCase<Void, VoidVerification> {
    public GoToClock(ClockDriver clockDriver, UseCaseContext useCaseContext) {
        super(clockDriver, useCaseContext);
    }

    @Override
    public UseCaseResult<Void, VoidVerification> execute() {
        var result = driver.goToClock();
        return new UseCaseResult<>(result, context, VoidVerification::new);
    }
}

