package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults;

public class GivenClock extends BaseGivenStep {
    private String time;

    public GivenClock(Given given) {
        super(given);
        withTime(GherkinDefaults.DEFAULT_TIME);
    }

    public GivenClock withTime(String time) {
        this.time = time;
        return this;
    }

    @Override
    void execute(SystemDsl app) {
        app.clock().returnsTime()
            .time(time)
            .execute()
            .shouldSucceed();
    }
}