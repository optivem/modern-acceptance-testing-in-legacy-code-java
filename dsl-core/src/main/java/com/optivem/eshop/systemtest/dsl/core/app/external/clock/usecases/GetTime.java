package com.optivem.eshop.systemtest.dsl.core.app.external.clock.usecases;

import com.optivem.eshop.systemtest.driver.port.external.clock.ClockDriver;
import com.optivem.eshop.systemtest.driver.port.external.clock.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.dsl.core.app.external.clock.usecases.base.BaseClockUseCase;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseContext;

public class GetTime extends BaseClockUseCase<GetTimeResponse, GetTimeVerification> {
    public GetTime(ClockDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public UseCaseResult<GetTimeResponse, GetTimeVerification> execute() {
        var result = driver.getTime();
        return new UseCaseResult<>(result, context, GetTimeVerification::new);
    }
}
