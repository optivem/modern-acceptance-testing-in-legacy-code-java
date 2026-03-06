package com.optivem.eshop.systemtest.dsl.port.when.steps;

import com.optivem.eshop.systemtest.dsl.port.when.steps.base.WhenStep;

public interface WhenSubmitReview extends WhenStep {
    WhenSubmitReview withRating(int rating);

    WhenSubmitReview withComment(String comment);
}
