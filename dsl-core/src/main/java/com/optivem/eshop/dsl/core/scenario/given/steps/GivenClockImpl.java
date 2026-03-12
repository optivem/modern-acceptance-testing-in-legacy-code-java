package com.optivem.eshop.dsl.core.scenario.given.steps;

import com.optivem.eshop.dsl.core.app.AppDsl;
import com.optivem.eshop.dsl.core.scenario.ScenarioDefaults;
import com.optivem.eshop.dsl.core.scenario.given.GivenImpl;
import com.optivem.eshop.dsl.port.given.steps.GivenClock;

public class GivenClockImpl extends BaseGivenStep implements GivenClock {
    private String time;

    public GivenClockImpl(GivenImpl given) {
        super(given);
        withTime(ScenarioDefaults.DEFAULT_TIME);
    }

    public GivenClockImpl withTime(String time) {
        this.time = time;
        return this;
    }

    @Override
    public void execute(AppDsl app) {
        app.clock().returnsTime()
            .time(time)
            .execute()
            .shouldSucceed();
    }
}


