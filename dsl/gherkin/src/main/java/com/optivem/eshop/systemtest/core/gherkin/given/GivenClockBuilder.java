package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

public class GivenClockBuilder extends BaseGivenBuilder {
    private String time;

    public GivenClockBuilder(GivenClause givenClause) {
        super(givenClause);
        withTime(GherkinDefaults.DEFAULT_TIME);
    }

    public GivenClockBuilder withTime(String time) {
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

