package com.optivem.eshop.systemtest.v7.acceptance;

import com.optivem.eshop.systemtest.v7.acceptance.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.channel.ChannelType;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;

class SubmitReviewNegativeTest extends BaseAcceptanceTest {
    @Disabled("AT - RED - TEST")
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

    // TODO: Cannot submit a review with rating outside 1 to 5
    // TODO: Cannot submit a review with a comment exceeding 500 characters
    // TODO: Cannot submit a duplicate review
    // TODO: Cannot submit review when product is not reviewable
}
