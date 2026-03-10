package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import static com.optivem.eshop.systemtest.dsl.core.scenario.ScenarioDefaults.*;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.app.shop.usecases.SubmitReviewVerification;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.SubmitReviewResponse;
import com.optivem.eshop.systemtest.dsl.port.when.steps.WhenSubmitReview;

public class WhenSubmitReviewImpl extends BaseWhenStep<SubmitReviewResponse, SubmitReviewVerification> implements WhenSubmitReview {
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

    @Override
    public WhenSubmitReviewImpl withRating(String rating) {
        this.rating = rating;
        return this;
    }

    @Override
    public WhenSubmitReviewImpl withComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    protected ExecutionResult<SubmitReviewResponse, SubmitReviewVerification> execute(AppDsl app) {
        throw new UnsupportedOperationException("TODO: DSL");
    }
}
