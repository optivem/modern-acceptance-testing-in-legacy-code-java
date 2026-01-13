package com.optivem.eshop.systemtest.core.clock.dsl.commands;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.BaseClockCommand;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.ClockUseCaseResult;
import com.optivem.test.dsl.UseCaseContext;
import com.optivem.test.dsl.VoidVerification;

import java.time.Instant;

public class ReturnsTime extends BaseClockCommand<Void, VoidVerification<UseCaseContext>> {

    private Instant time;

    public ReturnsTime(ClockDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public ReturnsTime time(Instant time) {
        this.time = time;
        return this;
    }

    public ReturnsTime time(String time) {
        return time(Instant.parse(time));
    }

    @Override
    public ClockUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {

        var request = ReturnsTimeRequest.builder()
            .time(time)
            .build();

        var result = driver.returnsTime(request);

        return new ClockUseCaseResult<>(
            result,
            context,
            VoidVerification::new
        );
    }
}
