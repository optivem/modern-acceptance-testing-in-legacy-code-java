package com.optivem.eshop.systemtest.dsl.core.scenario.given.steps;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenReview;

public class GivenReviewImpl extends BaseGivenStep implements GivenReview {

    public GivenReviewImpl(GivenImpl given) {
        super(given);
    }

    @Override
    public void execute(AppDsl app) {
        throw new UnsupportedOperationException("DSL not implemented yet");
    }
}
