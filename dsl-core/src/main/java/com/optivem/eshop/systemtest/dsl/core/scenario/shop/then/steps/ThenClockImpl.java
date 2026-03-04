package com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps;

import com.optivem.eshop.systemtest.dsl.core.app.clock.usecases.GetTimeVerification;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenClock;

public class ThenClockImpl implements ThenClock {
    private final GetTimeVerification verification;

    public ThenClockImpl(GetTimeVerification verification) {
        this.verification = verification;
    }

    @Override
    public ThenClock hasTime(String time) {
        verification.time(time);
        return this;
    }

    @Override
    public ThenClock hasTimeNotNull() {
        verification.timeIsNotNull();
        return this;
    }
}
