package com.optivem.eshop.systemtest.v7.acceptance;

import com.optivem.eshop.systemtest.v7.acceptance.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.channel.ChannelType;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.OrderStatus;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;

class SubmitReviewNegativeTest extends BaseAcceptanceTest {
    @Disabled("RED 1 - Tests")
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewOnNonDeliveredOrder() {
        scenario
                .given().order()
                .when().submitReview()
                .then().shouldFail()
                    .errorMessage("Order has not been delivered yet");
    }

    @Disabled("RED 1 - Tests")
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewWithRating0() {
        scenario
                .given().order()
                    .withStatus(OrderStatus.DELIVERED)
                .when().submitReview()
                    .withRating(0)
                .then().shouldFail()
                    .errorMessage("Rating must be between 1 and 5");
    }

    @Disabled("RED 1 - Tests")
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewWithRating6() {
        scenario
                .given().order()
                    .withStatus(OrderStatus.DELIVERED)
                .when().submitReview()
                    .withRating(6)
                .then().shouldFail()
                    .errorMessage("Rating must be between 1 and 5");
    }

    @Disabled("RED 1 - Tests")
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewWithCommentExceeding500Characters() {
        scenario
                .given().order()
                    .withStatus(OrderStatus.DELIVERED)
                .when().submitReview()
                    .withComment("A".repeat(501))
                .then().shouldFail()
                    .errorMessage("Comment must not exceed 500 characters");
    }

    @Disabled("RED 1 - Tests")
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotSubmitReviewWhenProductIsNotReviewable() {
        scenario
                .given().order()
                    .withStatus(OrderStatus.DELIVERED)
                .and().product()
                    .withReviewable(false)
                .when().submitReview()
                .then().shouldFail()
                    .errorMessage("Product is not reviewable");
    }

    // TODO: Cannot submit a duplicate review
}
