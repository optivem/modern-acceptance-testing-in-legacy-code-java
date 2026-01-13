package com.optivem.eshop.systemtest.core.clock.dsl;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.driver.ClockRealDriver;
import com.optivem.eshop.systemtest.core.clock.driver.ClockStubDriver;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.GetTime;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.GoToClock;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.ReturnsTime;
import com.optivem.commons.util.Closer;
import com.optivem.commons.dsl.UseCaseContext;

public class ClockDsl implements AutoCloseable {
    protected final ClockDriver driver;
    protected final UseCaseContext context;

    private ClockDsl(ClockDriver driver, UseCaseContext context) {
        this.driver = driver;
        this.context = context;
    }

    public ClockDsl(String baseUrl, UseCaseContext context) {
        this(createDriver(baseUrl, context), context);
    }

    private static ClockDriver createDriver(String baseUrl, UseCaseContext context) {
        return switch (context.getExternalSystemMode()) {
            case REAL -> new ClockRealDriver();
            case STUB -> new ClockStubDriver(baseUrl);
        };
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
