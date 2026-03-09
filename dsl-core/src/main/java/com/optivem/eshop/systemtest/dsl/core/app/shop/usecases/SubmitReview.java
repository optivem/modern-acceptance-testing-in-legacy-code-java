package com.optivem.eshop.systemtest.dsl.core.app.shop.usecases;

import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.SubmitReviewRequest;
import com.optivem.eshop.systemtest.dsl.core.app.shop.usecases.base.BaseShopUseCase;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.shared.VoidVerification;

public class SubmitReview extends BaseShopUseCase<Void, VoidVerification> {
    private String orderNumberResultAlias;
    private String rating;
    private String comment;

    public SubmitReview(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public SubmitReview orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
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
    public UseCaseResult<Void, VoidVerification> execute() {
        var orderNumber = context.getResultValue(orderNumberResultAlias);
        var request = SubmitReviewRequest.builder()
                .orderNumber(orderNumber)
                .rating(rating)
                .comment(comment)
                .build();
        var result = driver.submitReview(request);
        return new UseCaseResult<>(result, context, VoidVerification::new);
    }
}
