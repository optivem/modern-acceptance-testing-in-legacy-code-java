package com.optivem.eshop.systemtest.driver.adapter.shop.ui.client.pages;

import com.optivem.eshop.systemtest.driver.adapter.shared.client.playwright.PageClient;

import java.util.regex.Pattern;

public class ReviewPage extends BasePage {
    private static final String RATING_INPUT_SELECTOR = "[aria-label='Rating']";
    private static final String COMMENT_INPUT_SELECTOR = "[aria-label='Comment']";
    private static final String SUBMIT_REVIEW_BUTTON_SELECTOR = "[aria-label='Submit Review']";
    private static final String REVIEW_ID_REGEX = "Success! Review has been submitted with Review ID ([\\w-]+)";
    private static final int REVIEW_ID_MATCHER_GROUP = 1;
    private static final String REVIEW_ID_NOT_FOUND_ERROR = "Could not find review ID";

    public ReviewPage(PageClient pageClient) {
        super(pageClient);
    }

    public void inputRating(String rating) {
        pageClient.fill(RATING_INPUT_SELECTOR, rating);
    }

    public void inputComment(String comment) {
        pageClient.fill(COMMENT_INPUT_SELECTOR, comment);
    }

    public void clickSubmitReview() {
        pageClient.click(SUBMIT_REVIEW_BUTTON_SELECTOR);
    }

    public static String getReviewId(String successMessageText) {
        var pattern = Pattern.compile(REVIEW_ID_REGEX);
        var matcher = pattern.matcher(successMessageText);

        if (!matcher.find()) {
            throw new IllegalStateException(REVIEW_ID_NOT_FOUND_ERROR);
        }

        return matcher.group(REVIEW_ID_MATCHER_GROUP);
    }
}
