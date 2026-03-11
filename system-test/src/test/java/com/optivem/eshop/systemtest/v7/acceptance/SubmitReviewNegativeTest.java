package com.optivem.eshop.systemtest.v7.acceptance;

import com.optivem.eshop.systemtest.v7.acceptance.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.channel.ChannelType;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;

class SubmitReviewNegativeTest extends BaseAcceptanceTest {
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewOnNonDeliveredOrder() {
        scenario
                .given().order()
                    .withStatus("PLACED")
                .when().submitReview()
                    .withRating("5")
                .then().shouldFail()
                    .errorMessage("Order has not been delivered yet");
    }

    @Disabled("AT - RED - TEST")
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewWithRatingBelowMinimum() {
        scenario
                .given().order()
                    .withStatus("DELIVERED")
                .when().submitReview()
                    .withRating("0")
                .then().shouldFail()
                    .errorMessage("Rating must be between 1 and 5");
    }

    @Disabled("AT - RED - TEST")
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewWithRatingAboveMaximum() {
        scenario
                .given().order()
                    .withStatus("DELIVERED")
                .when().submitReview()
                    .withRating("6")
                .then().shouldFail()
                    .errorMessage("Rating must be between 1 and 5");
    }

    // TODO: Cannot submit a review with a comment exceeding 500 characters
    // TODO: Cannot submit a duplicate review
    // TODO: Cannot submit review when product is not reviewable
}
