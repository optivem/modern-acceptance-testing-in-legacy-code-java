package com.optivem.eshop.systemtest.core.clients.commons;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.optivem.eshop.systemtest.TestConfiguration;

public abstract class BasePage {
    protected final Page page;
    private final String baseUrl;
    protected final int timeoutMilliseconds;

    private static final int DEFAULT_TIMEOUT_SECONDS = 10;
    private static final int DEFAULT_TIMEOUT_MILLISECONDS = DEFAULT_TIMEOUT_SECONDS * 1000;

    public BasePage(Page page, String baseUrl, int timeoutMilliseconds) {
        this.page = page;
        this.baseUrl = baseUrl;
        this.timeoutMilliseconds = timeoutMilliseconds;
    }

    public BasePage(Page page, String baseUrl) {
        this(page, baseUrl, DEFAULT_TIMEOUT_MILLISECONDS);
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    protected void fill(String selector, String text) {
        var input = page.locator(selector);
        wait(input);
        input.fill(text);
    }

    protected void click(String selector) {
        var button = page.locator(selector);
        wait(button);
        button.click();
    }

    protected String readTextContent(String selector) {
        var locator = page.locator(selector);
        wait(locator);
        return locator.textContent();
    }

    protected String readInputValue(String selector) {
        var locator = page.locator(selector);
        wait(locator);
        return locator.inputValue();
    }

    protected boolean isHidden(String selector) {
        var locator = page.locator(selector);
        return locator.count() == 0;
    }

    protected void waitForHidden(String selector) {
        var waitForOptions = getWaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeoutMilliseconds);

        var locator = page.locator(selector);
        locator.waitFor(waitForOptions);
    }

    private void wait(Locator locator) {
        var waitForOptions = new Locator.WaitForOptions().setTimeout(timeoutMilliseconds);
        locator.waitFor(waitForOptions);
    }

    private Locator.WaitForOptions getWaitForOptions() {
        return new Locator.WaitForOptions().setTimeout(timeoutMilliseconds);
    }
}

