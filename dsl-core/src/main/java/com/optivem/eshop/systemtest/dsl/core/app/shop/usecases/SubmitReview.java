package com.optivem.eshop.systemtest.dsl.core.app.shop.usecases;

import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.SubmitReviewRequest;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.SubmitReviewResponse;
import com.optivem.eshop.systemtest.dsl.core.app.shop.usecases.base.BaseShopUseCase;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseResult;

public class SubmitReview extends BaseShopUseCase<SubmitReviewResponse, SubmitReviewVerification> {
    private String orderNumberResultAlias;
    private String reviewIdResultAlias;
    private String rating;
    private String comment;

    public SubmitReview(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public SubmitReview orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    public SubmitReview reviewId(String reviewIdResultAlias) {
        this.reviewIdResultAlias = reviewIdResultAlias;
        return this;
    }

    public SubmitReview rating(String rating) {
        this.rating = rating;
        return this;
    }

    public SubmitReview comment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public UseCaseResult<SubmitReviewResponse, SubmitReviewVerification> execute() {
        throw new UnsupportedOperationException("TODO: DSL");
    }
}
