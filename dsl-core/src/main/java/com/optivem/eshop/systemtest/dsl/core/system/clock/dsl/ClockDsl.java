package com.optivem.eshop.systemtest.dsl.core.system.clock.dsl;

import com.optivem.eshop.systemtest.driver.api.clock.ClockDriver;
import com.optivem.eshop.systemtest.dsl.core.system.clock.dsl.usecases.GetTime;
import com.optivem.eshop.systemtest.dsl.core.system.clock.dsl.usecases.GoToClock;
import com.optivem.eshop.systemtest.dsl.core.system.clock.dsl.usecases.ReturnsTime;
import com.optivem.commons.util.Closer;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;

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
