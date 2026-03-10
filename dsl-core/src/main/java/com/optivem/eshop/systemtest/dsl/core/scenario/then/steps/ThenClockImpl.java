package com.optivem.eshop.systemtest.dsl.core.scenario.then.steps;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.app.external.clock.usecases.GetTimeVerification;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultContext;
import com.optivem.eshop.systemtest.dsl.core.shared.VoidVerification;
import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenClock;

public class ThenClockImpl extends BaseThenStep<Void, VoidVerification> implements ThenClock {
    private final GetTimeVerification verification;

    public ThenClockImpl(AppDsl app, ExecutionResultContext executionResult, GetTimeVerification verification) {
        super(app, executionResult, null);
        this.verification = verification;
    }

    @Override
    public ThenClockImpl hasTime(String time) {
        verification.time(time);
        return this;
    }

    @Override
    public ThenClockImpl hasTime() {
        verification.timeIsNotNull();
        return this;
    }

    @Override
    public ThenClockImpl and() {
        return this;
    }
}

