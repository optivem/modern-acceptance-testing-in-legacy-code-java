package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.core.shared.VoidVerification;
import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.port.when.steps.WhenSubmitReview;

public class WhenSubmitReviewImpl extends BaseWhenStep<Void, VoidVerification> implements WhenSubmitReview {

    public WhenSubmitReviewImpl(AppDsl app) {
        super(app);
    }

    public WhenSubmitReviewImpl withRating(String rating) {
        throw new UnsupportedOperationException("TODO: DSL");
    }

    public WhenSubmitReviewImpl withRating(int rating) {
        throw new UnsupportedOperationException("TODO: DSL");
    }

    public WhenSubmitReviewImpl withComment(String comment) {
        throw new UnsupportedOperationException("TODO: DSL");
    }

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(AppDsl app) {
        throw new UnsupportedOperationException("TODO: DSL");
    }
}
