package com.optivem.eshop.systemtest.dsl.core.gherkin.given.steps;

import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.GherkinDefaults;
import com.optivem.eshop.systemtest.dsl.core.gherkin.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenClockPort;

public class GivenClockImpl extends BaseGivenStep implements GivenClockPort {
    private String time;

    public GivenClockImpl(GivenImpl given) {
        super(given);
        withTime(GherkinDefaults.DEFAULT_TIME);
    }

    public GivenClockImpl withTime(String time) {
        this.time = time;
        return this;
    }

    @Override
    public void execute(SystemDsl app) {
        app.clock().returnsTime()
            .time(time)
            .execute()
            .shouldSucceed();
    }
}