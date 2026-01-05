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
    
    public String getPageContent() {
        return page.content();
    }
    
    public Object evaluateScript(String script) {
        return page.evaluate(script);
    }

    public void fill(String selector, String text) {
        var input = page.locator(selector);
        wait(input);
        var processedText = text == null ? "" : text;
        input.fill(processedText);
    }

    public void setInputValue(String selector, String value) {
        var locator = page.locator(selector);
        wait(locator);
        var processedValue = value == null ? "" : value;
        
        System.out.println("[DEBUG] Setting input " + selector + " to value: '" + processedValue + "'");
        
        // Use Playwright's fill method which properly triggers React events
        // Playwright auto-waits for the element to be ready and handles React state updates
        locator.fill(processedValue);
        
        // Verify the value was set (locator.evaluate() will auto-wait for DOM updates)
        var actualValue = locator.evaluate("element => element.value");
        System.out.println("[DEBUG] After setting, input " + selector + " has value: '" + actualValue + "'");
    }

    public void click(String selector) {
        var button = page.locator(selector);
        wait(button);
        button.click();
    }

    public String readTextContent(String selector) {
        var locator = page.locator(selector);
        wait(locator);
        return locator.textContent();
    }

    public List<String> readAllTextContents(String selector) {
        var locator = page.locator(selector);
        wait(locator);
        return locator.allTextContents();
    }

    public List<String> readAllTextContentsWithoutWait(String selector) {
        // Use this when the selector intentionally matches multiple elements (e.g., table cells)
        // to avoid Playwright's strict mode violation
        var locator = page.locator(selector);
        return locator.allTextContents();
    }

    public boolean exists(String selector) {
        var locator = page.locator(selector);
        try {
            wait(locator);
            return locator.count() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String readInputValue(String selector) {
        var locator = page.locator(selector);
        wait(locator);
        return locator.inputValue();
    }

    public int readInputInteger(String selector) {
        var inputValue = readInputValue(selector);
        return Integer.parseInt(inputValue);
    }

    public BigDecimal readInputDecimal(String selector) {
        var inputValue = readInputValue(selector);
        return new BigDecimal(inputValue);
    }

    public BigDecimal readInputDecimal(String selector, String... remove) {
        var inputValue = readInputValue(selector);
        for (var ch : remove) {
            inputValue = inputValue.replace(ch, "");
        }
        return new BigDecimal(inputValue);
    }

    public boolean isHidden(String selector) {
        var locator = page.locator(selector);
        return locator.count() == 0;
    }

    public String getAttribute(String selector, String attributeName) {
        var locator = page.locator(selector);
        wait(locator);
        return locator.getAttribute(attributeName);
    }

    public void waitForHidden(String selector) {
        var waitForOptions = getWaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeoutMilliseconds);

        var locator = page.locator(selector);
        locator.waitFor(waitForOptions);
    }

    public void waitForVisible(String selector) {
        var waitForOptions = getWaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutMilliseconds);

        var locator = page.locator(selector);
        locator.waitFor(waitForOptions);
    }

    private void wait(Locator locator) {
        var waitForOptions = getWaitForOptions();
        locator.waitFor(waitForOptions);
    }

    private Locator.WaitForOptions getWaitForOptions() {
        return new Locator.WaitForOptions().setTimeout(timeoutMilliseconds);
    }

    public void waitForLoaded(String selector) {
        var locator = page.locator(selector);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(timeoutMilliseconds));

        page.waitForFunction(
                "selector => document.querySelector(selector)?.getAttribute('aria-busy') === 'false'",
                selector,
                new Page.WaitForFunctionOptions().setTimeout(timeoutMilliseconds)
        );
    }

    public String getLoadState(String selector) {
        return page.getAttribute(selector, "data-load-state");
    }

    public boolean isLoadStateSuccess(String selector) {
        var loadState = getLoadState(selector);
        return "success".equals(loadState);
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
