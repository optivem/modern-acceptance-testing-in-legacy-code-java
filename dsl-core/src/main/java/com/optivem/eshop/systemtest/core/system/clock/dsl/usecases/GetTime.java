package com.optivem.eshop.systemtest.core.system.clock.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.clock.ClockDriver;
import com.optivem.eshop.systemtest.driver.api.clock.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.core.system.clock.dsl.usecases.base.BaseClockCommand;
import com.optivem.eshop.systemtest.core.system.clock.dsl.usecases.base.ClockUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;

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
