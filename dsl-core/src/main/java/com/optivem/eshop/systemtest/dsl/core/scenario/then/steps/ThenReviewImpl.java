package com.optivem.eshop.systemtest.dsl.core.scenario.then.steps;

import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenReview;

public class ThenReviewImpl implements ThenReview {

    @Override
    public ThenReview hasRating(int expectedRating) {
        throw new UnsupportedOperationException("DSL not implemented yet");
    }

    @Override
    public ThenReview hasComment(String expectedComment) {
        throw new UnsupportedOperationException("DSL not implemented yet");
    }

    @Override
    public ThenReview hasReviewId() {
        throw new UnsupportedOperationException("DSL not implemented yet");
    }

    @Override
    public ThenReview hasTimestamp() {
        throw new UnsupportedOperationException("DSL not implemented yet");
    }

    @Override
    public ThenReview and() {
        return this;
    }
}
