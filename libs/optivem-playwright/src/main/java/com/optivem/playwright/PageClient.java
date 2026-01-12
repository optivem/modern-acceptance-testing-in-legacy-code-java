package com.optivem.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.math.BigDecimal;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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



    /**
     * Fills an input field with the specified value.
     * Null values are converted to empty string.
     * @param selector The CSS selector for the input element
     * @param value The value to fill (null will be converted to empty string)
     */
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
        locator.first().waitFor(getWaitForOptions());
        return locator.allTextContents();
    }

    public boolean exists(String selector) {
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

    public void waitForVisible(String selector) {
        var waitForOptions = getWaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutMilliseconds);

        var locator = page.locator(selector);
        locator.waitFor(waitForOptions);
    }

    private Locator.WaitForOptions getWaitForOptions() {
        return new Locator.WaitForOptions().setTimeout(timeoutMilliseconds);
    }

    private Locator getLocator(String selector, Locator.WaitForOptions waitForOptions) {
        var locator = page.locator(selector);
        locator.waitFor(waitForOptions);
        return locator;
    }

    private Locator getLocator(String selector) {
        var waitForOptions = getWaitForOptions();
        return getLocator(selector, waitForOptions);
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
