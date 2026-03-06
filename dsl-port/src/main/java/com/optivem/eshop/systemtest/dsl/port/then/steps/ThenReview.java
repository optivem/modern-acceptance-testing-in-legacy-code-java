package com.optivem.eshop.systemtest.dsl.port.then.steps;

public interface ThenReview {
    ThenReview hasRating(int expectedRating);

    ThenReview hasComment(String expectedComment);

    ThenReview hasReviewId();

    ThenReview hasTimestamp();

    ThenReview and();
}
