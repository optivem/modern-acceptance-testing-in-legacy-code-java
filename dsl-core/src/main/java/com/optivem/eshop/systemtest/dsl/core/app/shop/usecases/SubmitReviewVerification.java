package com.optivem.eshop.systemtest.dsl.core.app.shop.usecases;

import com.optivem.eshop.systemtest.driver.port.shop.dtos.SubmitReviewResponse;
import com.optivem.eshop.systemtest.dsl.core.shared.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseContext;

public class SubmitReviewVerification extends ResponseVerification<SubmitReviewResponse> {
    public SubmitReviewVerification(SubmitReviewResponse response, UseCaseContext context) {
        super(response, context);
    }

    public SubmitReviewVerification reviewId(String reviewIdResultAlias) {
        throw new UnsupportedOperationException("TODO: DSL");
    }
}
