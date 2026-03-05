package com.optivem.eshop.systemtest.dsl.core.app.clock;

import com.optivem.eshop.systemtest.driver.port.clock.ClockDriver;
import com.optivem.eshop.systemtest.dsl.core.app.clock.usecases.GetTime;
import com.optivem.eshop.systemtest.dsl.core.app.clock.usecases.GoToClock;
import com.optivem.eshop.systemtest.dsl.core.app.clock.usecases.ReturnsTime;
import com.optivem.common.Closer;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseContext;

public class ClockDsl implements AutoCloseable {
    protected final ClockDriver driver;
    protected final UseCaseContext context;

    public ClockDsl(ClockDriver driver, UseCaseContext context) {
        this.driver = driver;
        this.context = context;
    }

    @Override
    public void close() {
        Closer.close(driver);
    }

    public GoToClock goToClock() {
        return new GoToClock(driver, context);
    }

    public ReturnsTime returnsTime() {
        return new ReturnsTime(driver, context);
    }

    public GetTime getTime() {
        return new GetTime(driver, context);
    }
}



