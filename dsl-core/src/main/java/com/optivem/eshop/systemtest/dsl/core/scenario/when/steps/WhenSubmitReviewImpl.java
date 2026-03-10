package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.shared.VoidVerification;
import com.optivem.eshop.systemtest.dsl.port.when.steps.WhenSubmitReview;

import static com.optivem.eshop.systemtest.dsl.core.scenario.ScenarioDefaults.*;

public class WhenSubmitReviewImpl extends BaseWhenStep<Void, VoidVerification> implements WhenSubmitReview {
    private String orderNumber;
    private String reviewId;
    private String rating;
    private String comment;

    public WhenSubmitReviewImpl(AppDsl app) {
        super(app);
        withOrderNumber(DEFAULT_ORDER_NUMBER);
        withReviewId(DEFAULT_REVIEW_ID);
        withRating(DEFAULT_RATING);
        withComment(DEFAULT_COMMENT);
    }

    public WhenSubmitReviewImpl withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public WhenSubmitReviewImpl withReviewId(String reviewId) {
        this.reviewId = reviewId;
        return this;
    }

    public WhenSubmitReviewImpl withRating(String rating) {
        this.rating = rating;
        return this;
    }

    public WhenSubmitReviewImpl withComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(AppDsl app) {
        throw new UnsupportedOperationException("TODO: DSL");
    }
}
