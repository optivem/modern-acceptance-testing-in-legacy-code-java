package com.optivem.commons.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;

public class PageClient {
    // Increased default timeout for parallel test execution
    private static final int DEFAULT_TIMEOUT_SECONDS = 30;
    private static final int DEFAULT_TIMEOUT_MILLISECONDS = DEFAULT_TIMEOUT_SECONDS * 1000;
    private final Page page;
    private final String baseUrl;
    private final int timeoutMilliseconds;

    private PageClient(Builder builder) {
        this.page = builder.page;
        this.baseUrl = builder.baseUrl;
        this.timeoutMilliseconds = builder.timeoutMilliseconds;
    }

    public static Builder builder(Page page) {
        return new Builder(page);
    }

    public void fill(String selector, String value) {
        var locator = getLocator(selector);
        var processedValue = value == null ? "" : value;
        locator.fill(processedValue);
    }

    public void click(String selector) {
        var locator = getLocator(selector);
        locator.click();
    }

    public String readTextContent(String selector) {
        var locator = getLocator(selector);
        return locator.textContent();
    }

    public List<String> readAllTextContents(String selector) {
        var locator = page.locator(selector);
        // Wait for at least one element to be visible
        // allTextContents() doesn't trigger strict mode - it's designed for multiple elements
        locator.first().waitFor(getDefaultWaitForOptions());
        return locator.allTextContents();
    }

    public boolean isVisible(String selector) {
        try {
            var locator = getLocator(selector);
            return locator.count() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isHidden(String selector) {
        var locator = page.locator(selector);
        return locator.count() == 0;
    }

    private Locator getLocator(String selector, Locator.WaitForOptions waitForOptions) {
        var locator = page.locator(selector);
        locator.waitFor(waitForOptions);

        if(locator.count() == 0) {
            throw new RuntimeException("No elements found for selector: " + selector);
        }

        return locator;
    }

    private Locator getLocator(String selector) {
        var waitForOptions = getDefaultWaitForOptions();
        return getLocator(selector, waitForOptions);
    }
    private Locator.WaitForOptions getDefaultWaitForOptions() {
        return new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutMilliseconds);
    }

    public static class Builder {
        private final Page page;
        private String baseUrl = null;
        private int timeoutMilliseconds = DEFAULT_TIMEOUT_MILLISECONDS;

        private Builder(Page page) {
            this.page = page;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder timeoutMilliseconds(int timeoutMilliseconds) {
            this.timeoutMilliseconds = timeoutMilliseconds;
            return this;
        }

        public PageClient build() {
            return new PageClient(this);
        }
    }
}
