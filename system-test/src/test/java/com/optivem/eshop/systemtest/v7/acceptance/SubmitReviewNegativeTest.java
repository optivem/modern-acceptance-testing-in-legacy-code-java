package com.optivem.eshop.systemtest.v7.acceptance;

import com.optivem.eshop.systemtest.v7.acceptance.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.channel.ChannelType;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.OrderStatus;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.params.provider.ValueSource;

class SubmitReviewNegativeTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewOnNonDeliveredOrder() {
        scenario
                .given().order()
                    .withStatus(OrderStatus.PLACED)
                .when().submitReview()
                .then().shouldFail()
                    .errorMessage("Order has not been delivered yet");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ValueSource(strings = {"0", "6"})
    void cannotSubmitReviewWithRatingOutsideRange(String rating) {
        scenario
                .when().submitReview()
                    .withRating(rating)
                .then().shouldFail()
                    .fieldErrorMessage("rating", "Rating must be between 1 and 5");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewWithCommentExceedingMaxLength() {
        scenario
                .when().submitReview()
                    .withComment("x".repeat(501))
                .then().shouldFail()
                    .fieldErrorMessage("comment", "Comment must not exceed 500 characters");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitDuplicateReview() {
        scenario
                .given().order()
                    .withStatus(OrderStatus.DELIVERED)
                .and().review()
                .when().submitReview()
                .then().shouldFail()
                    .errorMessage("A review has already been submitted for this order");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewWhenProductIsNotReviewable() {
        scenario
                .given().order()
                    .withStatus(OrderStatus.DELIVERED)
                .and().product()
                    .isNotReviewable()
                .when().submitReview()
                .then().shouldFail()
                    .errorMessage("This product is not eligible for reviews");
    }
}
