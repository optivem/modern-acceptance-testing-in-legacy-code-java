package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.port.then.ThenResultStage;
import com.optivem.eshop.systemtest.dsl.port.when.steps.WhenSubmitReview;

public class WhenSubmitReviewImpl implements WhenSubmitReview {

    public WhenSubmitReviewImpl(AppDsl app) {
    }

    @Override
    public WhenSubmitReviewImpl withRating(int rating) {
        return this;
    }

    @Override
    public WhenSubmitReviewImpl withComment(String comment) {
        return this;
    }

    @Override
    public ThenResultStage then() {
        throw new UnsupportedOperationException("DSL not implemented yet");
    }
}
