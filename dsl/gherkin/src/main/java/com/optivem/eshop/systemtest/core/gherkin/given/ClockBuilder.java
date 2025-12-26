package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

import java.time.Instant;

public class ClockBuilder {
    private final GivenClause givenClause;
    private Instant time;

    public ClockBuilder(GivenClause givenClause) {
        this.givenClause = givenClause;
        withTime(GherkinDefaults.DEFAULT_TIME);
    }

    public ClockBuilder withTime(Instant time) {
        this.time = time;
        return this;
    }

    public GivenClause and() {
        return givenClause;
    }

    public WhenClause when() {
        return givenClause.when();
    }

    void execute(SystemDsl app) {
        app.clock().returnsTime()
            .time(time)
            .execute()
            .shouldSucceed();
    }
}

