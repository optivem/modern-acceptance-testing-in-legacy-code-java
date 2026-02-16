package com.optivem.eshop.systemtest.core.clock.dsl.usecases;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.dsl.usecases.base.BaseClockCommand;
import com.optivem.eshop.systemtest.core.clock.dsl.usecases.base.ClockUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.VoidVerification;

public class GoToClock extends BaseClockCommand<Void, VoidVerification> {
    public GoToClock(ClockDriver clockDriver, UseCaseContext useCaseContext) {
        super(clockDriver, useCaseContext);
    }

    @Override
    public ClockUseCaseResult<Void, VoidVerification> execute() {
        var result = driver.goToClock();
        return new ClockUseCaseResult<>(result, context, VoidVerification::new);
    }
}
