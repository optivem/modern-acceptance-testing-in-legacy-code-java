package com.optivem.eshop.systemtest.dsl.core.app.clock.usecases;

import com.optivem.eshop.systemtest.driver.port.clock.ClockDriver;
import com.optivem.eshop.systemtest.driver.port.clock.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.dsl.core.app.clock.usecases.base.BaseClockUseCase;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.app.VoidVerification;

public class ReturnsTime extends BaseClockUseCase<Void, VoidVerification> {
    private String time;

    public ReturnsTime(ClockDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public ReturnsTime time(String time) {
        this.time = time;
        return this;
    }

    @Override
    public UseCaseResult<Void, VoidVerification> execute() {
        var request = ReturnsTimeRequest.builder()
                .time(time)
                .build();

        var result = driver.returnsTime(request);

        return new UseCaseResult<>(result, context, VoidVerification::new);
    }
}

