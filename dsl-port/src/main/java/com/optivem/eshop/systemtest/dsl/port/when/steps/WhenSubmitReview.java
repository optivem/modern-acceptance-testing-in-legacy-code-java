package com.optivem.eshop.systemtest.dsl.port.when.steps;

import com.optivem.eshop.systemtest.dsl.port.when.steps.base.WhenStep;

public interface WhenSubmitReview extends WhenStep {
    WhenSubmitReview withOrderNumber(String orderNumber);


    WhenSubmitReview withRating(String rating);

    WhenSubmitReview withComment(String comment);
}
