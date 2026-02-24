package com.optivem.eshop.systemtest.core.clock.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.driver.api.clock.driver.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.core.clock.dsl.usecases.base.BaseClockCommand;
import com.optivem.eshop.systemtest.core.clock.dsl.usecases.base.ClockUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.VoidVerification;

public class ReturnsTime extends BaseClockCommand<Void, VoidVerification> {

    private String time;

    public ReturnsTime(ClockDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public ReturnsTime time(String time) {
        this.time = time;
        return this;
    }

    @Override
    public ClockUseCaseResult<Void, VoidVerification> execute() {
        var request = ReturnsTimeRequest.builder()
                .time(time)
                .build();

        var result = driver.returnsTime(request);

        return new ClockUseCaseResult<>(result, context, VoidVerification::new);
    }
}
