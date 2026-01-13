package com.optivem.eshop.systemtest.core.clock.dsl.commands;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.BaseClockCommand;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.ClockUseCaseResult;
import com.optivem.test.dsl.UseCaseContext;
import com.optivem.test.dsl.VoidVerification;

public class GoToClock extends BaseClockCommand<Void, VoidVerification<UseCaseContext>> {
    public GoToClock(ClockDriver clockDriver, UseCaseContext useCaseContext) {
        super(clockDriver, useCaseContext);
    }

    @Override
    public ClockUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {
        var result = driver.goToClock();
        return new ClockUseCaseResult<>(result, context, VoidVerification::new);
    }
}
