package com.optivem.eshop.systemtest.core.clock.dsl.commands;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.BaseClockCommand;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.ClockUseCaseResult;
import com.optivem.eshop.systemtest.core.clock.dsl.verifications.GetTimeVerification;
import com.optivem.test.dsl.UseCaseContext;

public class GetTime extends BaseClockCommand<GetTimeResponse, GetTimeVerification> {

    public GetTime(ClockDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public ClockUseCaseResult<GetTimeResponse, GetTimeVerification> execute() {
        var result = driver.getTime();
        return new ClockUseCaseResult<>(result, context, GetTimeVerification::new);
    }
}
